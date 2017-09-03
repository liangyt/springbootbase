/**
 * Created by liangyt on 17/9/3.
 */
var index = null;
(function () {
    $(function () {
        index = new Vue({
            el: '#admin',
            mixins: [commonMixin],
            data: {
                defaultActive: null
            }
        })
    });
})()