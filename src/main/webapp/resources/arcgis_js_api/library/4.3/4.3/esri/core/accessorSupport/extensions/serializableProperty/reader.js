// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["require","exports","dojo/_base/lang","./type"],function(q,l,m,n){function p(a,b,d,c){if(b)return f(a);if(g(a)){var e=f(a.prototype.itemType);return function(b,d){return(b=e(b,d))?new a(b):b}}return h(a)}function h(a){return a.prototype.read?function(b,d,c){return null==b?b:(new a).read(b,c)}:a.fromJSON}function f(a){var b=h(a);return function(a,c,e){return null==a?a:Array.isArray(a)?a.map(function(a){return b(a,null,e)}):[b(a,null,e)]}}function g(a){return n.isCollection(a)&&k(a.prototype.itemType)}
function k(a){return!!a&&(!!a.prototype.read||!!a.fromJSON||g(a))}l.create=function(a,b,d,c){(!c.read||!c.read.reader&&!1!==c.read.enabled)&&k(a)&&m.setObject("read.reader",p(a,b,d,c),c)}});