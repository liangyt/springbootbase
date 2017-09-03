/**
 * Created by liangyt on 17/8/27.
 */
var login = new Vue({
    el: '#login',
    data: {
        rules: {
            username: [
                {
                    required: true, message: '请输入用户名',trigger: 'blur'
                }
            ],
            password: [
                {
                    required: true, message: '请输入密码',trigger: 'blur'
                }
            ]
        },
        form: {
            username: '',
            password: ''
        },
        formInline: {
            user: '',
            region: ''
        }
    },
    methods: {
        submit: function (form) {
            var self = this;
            self.$refs[form].validate(function (valid) {
                console.log(valid)
                axios.post('/api/login', self.form)
                    .then(function (res) {
                        if (res.data.code == 1) {
                            window.location.href = '/'
                        }
                        else {
                            self.$message({
                                type: 'error',
                                message: res.data.message
                            })
                        }
                    })
            })
        },
        resetForm: function (form) {
            this.$refs[form].resetFields();
        }
    }
});