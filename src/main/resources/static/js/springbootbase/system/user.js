/**
 * Created by liangyt on 17/8/28.
 */
var user = null;
(function() {
    $(function () {

        var roles = []; // 所有的角色

        user = new Vue({
            el:'#admin',
            mixins: [commonMixin],
            data: {
                defaultActive: '1-1',
                searchForm: {
                    keyword: ''
                },

                tableData: [],
                currentPage: 1,
                pageSize: 10,
                total: 0,

                dialogFormVisible: false,
                addform: {
                    id: '',
                    username: '',
                    name: ''
                },
                formLabelWidth: '120px',
                addrules: {
                    name: [
                        {
                            required: true, message: '请输入用户名',trigger: 'blur'
                        }
                    ],
                    username: [
                        {
                            required: true, message: '请输入用户编号',trigger: 'blur'
                        }
                    ]
                },

                deleteDialogVisible: false,
                // 选择的用户
                currentUser: {},

                // 角色相关
                roleTreeNodes: [],
                defaultCheckedKeys: [], // 默认选择节点
                setRoleDiallogVisible: false, // 设置角色对话框默认不显示
                defaultProps: {
                    label: 'rolename'
                }
            },
            created: function () {
                this.fetchAllRoles();
                this.search();
            },
            methods: {
                // 取得角色列表
                fetchAllRoles: function () {
                    var self = this;
                    axios.get('/api/role/all')
                        .then(function (res) {
                            if (res.data.code == 1) {
                                roles = res.data.data;
                            }
                        })
                },
                search: function () {
                    this.currentPage = 1;
                    this.query();
                },
                // 查询
                query: function () {
                    var self = this;
                    axios.get('/api/user/list', {params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        keyword: this.searchForm.keyword
                    }})
                    .then(function (res) {
                        if (res.data.code == 1) {
                            self.tableData = res.data.data.rows;
                            self.total = res.data.data.total;
                        }
                    })
                },
                // 设置用户角色
                setRole: function(row) {
                    var self = this;
                    self.currentUser = row;
                    axios.get('/api/user/role/' + row.id)
                        .then(function (res) {
                            if (res.data.code == 1) {
                                self.defaultCheckedKeys = res.data.data;
                                self.roleTreeNodes = $.extend(true, [], roles);
                                self.setRoleDiallogVisible = true;
                            }
                        })
                },
                // 每页显示条数改变事件
                handleSizeChange: function(val) {
                    this.pageSize = val;
                    this.currentPage = 1;
                    this.query();
                },
                // 翻页事件
                handleCurrentChange: function(val) {
                    this.currentPage = val;
                    this.query();
                },
                // 编辑用户  用户数据比较少，直接拿过来使用了，内容比较大的话，需要通过详细接口获取
                editUser: function (row) {
                    this.addform = row;
                    this.dialogFormVisible = true;
                },
                // 添加
                addDialog: function () {
                    this.addform = {
                        id: '',
                        username: '',
                        user: ''
                    }
                    this.dialogFormVisible = true;
                    
                },
                // 用户 弹窗确定按钮
                addOk: function () {
                    var self = this;
                    this.$refs['addform'].validate(function(valid) {
                        if (valid) {
                            axios.post('/api/user', self.addform)
                            .then(function (res) {
                                if (res.data.code == 1) {
                                    self.$message({
                                        type: 'success',
                                        message: res.data.message
                                    })
                                    self.dialogFormVisible = false;
                                    self.search();
                                }
                                else {
                                    self.$message({
                                        type: 'error',
                                        message: res.data.message
                                    })
                                }
                            }).catch(function () {

                            })
                        }
                    });
                },
                // 删除
                deleteUser: function (row) {
                    this.currentUser = row;
                    this.deleteDialogVisible = true;
                },
                // 确认删除用户
                deleteOk: function () {
                    var self = this;
                    axios.post('/api/user/del/' + this.currentUser.id)
                        .then(function (res) {
                            if (res.data.code ==1) {
                                self.$message({
                                    type: 'success',
                                    message: '删除成功'
                                })
                                self.query();
                                self.deleteDialogVisible = false;
                            }
                            else {
                                self.$message({
                                    type: 'error',
                                    message: res.data.message
                                })
                            }
                        })
                },
                // 角色设置OK
                setRoleOk: function () {
                    var self = this;
                    var nodes = self.$refs.rolesTree.getCheckedNodes(true);
                    var _send = []
                    nodes.forEach(function (item, ind) {
                        _send.push(item.id)
                    })
                    axios.post('/api/user/setrole/' + self.currentUser.id, _send )
                        .then(function (res) {
                            if (res.data.code == 1) {
                                self.setRoleDiallogVisible = false;
                                self.$message({
                                    type: 'success',
                                    message: '设置成功'
                                })
                            }
                            else {
                                self.$message({
                                    type: 'error',
                                    message: res.data.message
                                })
                            }
                        })
                }
            }

        })
    })
})();