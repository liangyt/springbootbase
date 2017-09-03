/**
 * Created by liangyt on 17/8/31.
 */
var permission = null;
(function () {
    $(function () {
        permission = new Vue({
            el: '#admin',
            mixins: [commonMixin],
            data: {
                defaultActive: '1-3',
                defaultProps: {
                    children: 'children',
                    label: 'permissionName'
                },
                permissionTree: [],
                currentNode: null, // 当前选择节点

                // 添加编辑框
                dialogFormVisible: false,
                formLabelWidth: '120px',
                addrules: {
                    permissionName: [
                        {
                            required: true, message: '请输入名称',trigger: 'blur'
                        }
                    ],
                    permissionCode: [
                        {
                            required: true, message: '请输入编码',trigger: 'blur'
                        }
                    ]
                },
                form: {
                    id: '',
                    pid: '',
                    permissionName: '',
                    permissionCode: '',
                    url: '',
                    status: 0
                }
            },
            created: function () {
                var self = this;
                axios.get('/api/permission/list')
                    .then(function (res) {
                        if (res.data.code == 1) {
                            // res.data.data.disabled = true;
                            self.permissionTree = [res.data.data];
                        }
                    })
            },
            methods: {
                // 点击树节点的时候保存被点击节点
                handleNodeClick: function (self, node) {
                    this.currentNode = self;
                },
                // 编辑 
                edit: function () {
                    if (null == this.currentNode) {
                        this.$message({
                            type: 'error',
                            message: '未选择任何节点'
                        })
                        return;
                    }
                    if (this.currentNode.pid == 0) {
                        this.$message({
                            type: 'error',
                            message: '根节点不能编辑'
                        })
                        return;
                    }
                    this.form = this.currentNode
                    this.dialogFormVisible = true
                },
                // 新增子节点
                add: function () {
                    if (null == this.currentNode) {
                        this.$message({
                            type: 'error',
                            message: '未选择任何节点'
                        })
                        return;
                    }
                    this.form = {
                        id: '',
                        pid: '',
                        permissionName: '',
                        permissionCode: '',
                        url: '',
                        status: 0
                    }
                    this.dialogFormVisible = true
                },
                // 确定
                ok: function () {
                    var self = this;

                    this.$refs['addform'].validate(function(valid) {
                        if (valid) {
                            // 如果是新增
                            var isAddLeaf = false;
                            if (!self.form.id) {
                                isAddLeaf = true;
                                self.form.pid = self.currentNode.id;
                            }

                            axios.post('/api/permission', self.form)
                                .then(function (res) {
                                    if (res.data.code == 1) {
                                        if (isAddLeaf) {
                                            self.currentNode.children = self.currentNode.children ? self.currentNode.children : [];
                                            self.currentNode.children.push(res.data.data)
                                        }
                                        self.$message({
                                            type: 'success',
                                            message: res.data.message
                                        })
                                        self.dialogFormVisible = false
                                    }
                                    else {
                                        self.$message({
                                            type: 'error',
                                            message: res.data.message
                                        })
                                    }
                                })
                        }
                    });
                },

                /**
                 * 关于添加和删除 树的节点，element ui 也有自已的处理方式，可以参考官网例子
                 */

                // 删除
                deletePermission: function () {
                    if (null == this.currentNode) {
                        this.$message({
                            type: 'error',
                            message: '未选择任何节点'
                        })
                        return;
                    }

                    if (this.currentNode.children && this.currentNode.children.length > 0) {
                        this.$message({
                            type: 'error',
                            message: '请选删除叶子节点'
                        })
                        return;
                    }

                    var self = this;
                    self.$confirm('是否删除该节点？', '警告', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        axios.post('/api/permission/del/' + self.currentNode.id)
                            .then(function (res) {
                                if  (res.data.code == 1) {
                                     self.$message({
                                         message: '删除成功'
                                     })

                                    // self.currentNode = null
                                    self.deleteTreeLeaf(self.permissionTree[0].children, self.currentNode.id);
                                }
                                else {
                                    self.$message({
                                        type: 'error',
                                        message: res.data.message
                                    })
                                }
                            })
                    }).catch(function () {

                    })
                },
                // 执行删除叶子节点操作
                deleteTreeLeaf: function (nodes, id) {
                    var self = this;
                    if (!nodes || nodes.length == 0) return;

                    for (var i = nodes.length - 1; i >= 0; i-- ) {
                        // 相等的时候删除节点
                        if (nodes[i].id == id) {
                            nodes.splice(i, i + 1)
                            return;
                        }
                        if (nodes[i].children && nodes[i].children.length > 0) {
                            self.deleteTreeLeaf(nodes[i].children, id);
                        }
                    }
                }
            }
        })
    });
})()