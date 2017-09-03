var gulp = require('gulp');
var browserSync = require('browser-sync').create();
// 开发用
gulp.task('server', function () {
    browserSync.init({
        server: {
            baseDir: '.'
        }
    });
    browserSync.watch("static/css/**/*.css", function (event, file) {
        if (event === "change") {
            browserSync.reload("*.css");
        }
    });
    browserSync.watch("static/js/**/*.js", function (event, file) {
        if (event === "change") {
            browserSync.reload("*.js");
        }
    });
    browserSync.watch("templates/**/*.html").on("change", browserSync.reload);
});