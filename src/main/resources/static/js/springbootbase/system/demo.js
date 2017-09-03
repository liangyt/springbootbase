/**
 * Created by liangyt on 17/8/27.
 */
/**
 * Created by liangyt on 17/8/27.
 */
var login = new Vue({
    el: '#demo',
    data: {
        formInline: {
            user: '',
            region: ''
        },
        tableData: [{
            date: '2016-05-03',
            name: '王小虎',
            province: '上海',
            city: '普陀区',
            address: '上海市普陀区金沙江路 1518 弄',
            zip: 200333
        }, {
            date: '2016-05-02',
            name: '王小虎',
            province: '上海',
            city: '普陀区',
            address: '上海市普陀区金沙江路 1518 弄',
            zip: 200333
        }, {
            date: '2016-05-04',
            name: '王小虎',
            province: '上海',
            city: '普陀区',
            address: '上海市普陀区金沙江路 1518 弄',
            zip: 200333
        }, {
            date: '2016-05-01',
            name: '王小虎',
            province: '上海',
            city: '普陀区',
            address: '上海市普陀区金沙江路 1518 弄',
            zip: 200333
        }],
        currentPage4: 4,
        dialogVisible: false,
        dialogFormVisible: false,
        form: {
            name: '',
            region: '',
            date1: '',
            date2: '',
            delivery: false,
            type: [],
            resource: '',
            desc: ''
        },
        formLabelWidth: '120px'
    },
    methods: {
        handleClick: function() {
            console.log(1);
        },
        handleSizeChange: function(val) {
            console.log('每页 ' + val+ ' 条');
        },
        handleCurrentChange: function(val) {
            console.log('当前页: ' + val);
        },
        handleClose: function(done) {
            this.$confirm('确认关闭？')
                .then(function() {
                    done();
            }).catch(function() {});
        },
        addDialog: function () {
            this.dialogFormVisible = true
        }
    }
});