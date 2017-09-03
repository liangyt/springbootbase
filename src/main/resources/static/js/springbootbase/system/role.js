/**
 * Created by liangyt on 17/8/28.
 */
var role = null;
(function() {
    $(function () {
        var permimssionNodes = [];
        role = new Vue({
            el:'#admin',
            mixins: [commonMixin],
            data: {
                defaultActive: '1-2',
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
                    rolename: '',
                    description: '',
                    status: 0
                },
                formLabelWidth: '120px',
                addrules: {
                    rolename: [
                        {
                            required: true, message: '请输入角色名称',trigger: 'blur'
                        }
                    ]
                },

                deleteDialogVisible: false,
                // 待删除的角色
                currentRole: {},

                // 设置功能权限
                setPermissionDiallogVisible: false, // 功能权限对话框
                permissionTreeNodes: [], // 功能权限树
                defaultCheckedKeys: [], // 默认选择节点
                defaultProps: {
                    children: 'children',
                    label: 'permissionName'
                },

                tmpMap: {}, // 临时存放所有的需要提交的菜单功能ID
                tmpMapLeaf: {}
            },
            created: function () {
                this.search();
                this.initPermissionNodeData();
            },
            methods: {
                // 初始化菜单功能数据
                initPermissionNodeData: function () {
                    axios.get('/api/permission/list')
                        .then(function (res) {
                            if (res.data.code == 1) {
                                permimssionNodes = [res.data.data];
                            }
                        })
                },
                // 查询按钮
                search: function () {
                    this.currentPage = 1;
                    this.query();
                },
                // 根据条件查询列表数据
                query: function () {
                    var self = this;
                    axios.get('/api/role/list', {params: {
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
                // 设置角色权限
                setPermission: function(row) {
                    var self = this;

                    self.currentRole = row;

                    axios.get('/api/role/permission/' + row.id)
                        .then(function (res) {
                            if (res.data.code == 1) {
                                self.tmpMapLeaf = {};
                                self.defaultCheckedKeys = [];

                                self.returnLeaf(res.data.data)

                                res.data.data.forEach(function (id) {
                                    if (self.tmpMapLeaf[id]) self.defaultCheckedKeys.push(id);
                                })

                                self.permissionTreeNodes = $.extend(true, [], permimssionNodes);
                                self.setPermissionDiallogVisible = true;
                            }
                            else {
                                self.$message({
                                    type: 'error',
                                    message: res.data.message
                                })
                            }
                        })
                },
                // 过滤节点，只返回叶子节点
                returnLeaf: function (allNode) {
                    var self = this;
                    allNode.forEach(function (nodeId) {
                        self.isLeaf(permimssionNodes, nodeId)
                    })
                },
                isLeaf: function (nodes, nodeId) {
                    var self = this;
                    for(var i = 0; i < nodes.length; i++) {
                        if (nodes[i].id == nodeId) {
                            if (!nodes[i].children || nodes[i].children.length == 0)
                                self.tmpMapLeaf[nodeId] = 1
                            else self.tmpMapLeaf[nodeId] = 0

                            return;
                        }
                        else {
                            nodes[i].children && self.isLeaf(nodes[i].children, nodeId);
                        }
                    }
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
                editRole: function (row) {
                    this.addform = row;
                    this.dialogFormVisible = true;
                },
                // 添加
                addDialog: function () {
                    this.addform = {
                        id: '',
                        rolename: '',
                        description: '',
                        status: 0
                    }
                    this.dialogFormVisible = true;

                },
                // 用户 弹窗确定按钮
                addOk: function () {
                    var self = this;
                    this.$refs['addform'].validate(function(valid) {
                        if (valid) {
                            axios.post('/api/role', self.addform)
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
                deleteRole: function (row) {
                    this.currentRole = row;
                    this.deleteDialogVisible = true;
                },
                // 确认删除用户
                deleteOk: function () {
                    var self = this;
                    axios.post('/api/role/del/' + this.currentRole.id)
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
                // 设置角色功能弹窗确定按钮
                setPermissionOk: function () {
                    /**
                     * 不知道什么原因， element ui 这个树控件不能返回未全选叶子节点的父节点，那就只有自已处理了
                     */
                    var self = this;
                    // 所有的叶子节点
                    var allLeafNode = self.$refs.permissionTree.getCheckedNodes(true);
                    self.tmpMap = {};
                    allLeafNode.forEach(function (item) {
                        self.tmpMap[item.id] = 1;
                        self.findParentId(permimssionNodes, item.pid);
                    });

                    // 封装所有的功能节点到列表
                    var sendPermissions = [];
                    for(id in self.tmpMap) {
                        sendPermissions.push(id);
                    }

                    axios.post('/api/role/setpermission/' + self.currentRole.id, sendPermissions)
                        .then(function (res) {
                            if (res.data.code == 1) {
                                self.setPermissionDiallogVisible = false;
                                self.$message({
                                    type: 'success',
                                    message: res.data.message
                                })
                            }
                            else {
                                self.$message({
                                    type: 'error',
                                    message: res.data.message
                                })
                            }

                        })
                },
                // 根据ID查找父节点
                findParentId: function (nodes, id) {
                    var self = this;
                    // 如果已存在，则返回
                    if (self.tmpMap[id]) {
                        return;
                    }
                    nodes.forEach(function (node) {
                        if (node.id == id) {
                            self.tmpMap[node.id] = 1;
                            // 如果找到了则接着到上级
                            self.findParentId(permimssionNodes, node.pid);
                        }
                        else {
                            // 如果没有找到则接着向下找
                            node.children && self.findParentId(node.children, id);
                        }
                    })
                }
            }

        })
    })
})();