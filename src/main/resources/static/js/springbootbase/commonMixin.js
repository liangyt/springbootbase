/**
 * Created by liangyt on 17/8/29.
 */
var commonMixin = {
    data: {
        defaultOpeneds: ['0']
    },
    methods: {
        // 跳转
        goTo: function (url) {
            window.location.href = url;
        },
        // 退出
        logout: function () {
            var self = this;
            self.$confirm('确定退出系统吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios.post('/api/logout')
                    .then(function (res) {
                        if  (res.data.code == 1) {
                            self.$message({
                                message: res.data.message
                            })
                            window.location.href = '/login';
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
        }
    }
}