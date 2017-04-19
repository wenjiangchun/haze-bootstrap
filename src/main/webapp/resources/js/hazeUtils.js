/**
 * 添加window全局对象方法，判断对象是否未定义，或者为null和空字符串。
 * <p><b>说明：</b>该方法一般用在判断id等不为空的情况。比如修改删除数据等情况下。</p>
 * @param object 对象参数
 * @returns {boolean} 当object未定义，为null或空字符串时返回false,否则返回true。
 */
window.notNull = function(object) {
    if (object == undefined) {
        alert(object + "未定义！");
        return false;
    } else if (object == null) {
        alert(object + "不能为Null！");
        return false;
    } else if (object == "") {
        alert(object + "不能为空！");
        return false;
    } else {
        return true;
    }
}

