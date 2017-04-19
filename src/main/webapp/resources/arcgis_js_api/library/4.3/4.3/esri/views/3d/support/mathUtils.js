// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["../lib/glMatrix"],function(p){function n(a){for(var b in a){var c=a[b];"function"===typeof c&&(a[b]=c.bind(a))}return a}var d=p.vec3d,h=p.mat4d,l=d.create(),m=h.create(),q=d.create(),r=d.create(),f={deg2rad:function(a){return a*Math.PI/180},rad2deg:function(a){return 180*a/Math.PI},asin:function(a){return Math.asin(1<a?1:-1>a?-1:a)},acos:function(a){return Math.acos(1<a?1:-1>a?-1:a)},log2:Math.log2||function(a){return Math.log(a)/Math.LN2},fovx2fovy:function(a,b,c){return 2*Math.atan(c*Math.tan(.5*
a)/b)},fovy2fovx:function(a,b,c){return 2*Math.atan(b*Math.tan(.5*a)/c)},lerp:function(a,b,c){return a+(b-a)*c},bilerp:function(a,b,c,e,g,d){a+=(b-a)*g;return a+(c+(e-c)*g-a)*d},slerp:function(a,b,c,e){e||(e=a);var g=d.length(a),k=d.length(b),f=d.dot(a,b)/g/k;.999999999999>f&&(d.cross(a,b,l),h.identity(m),h.rotate(m,c*Math.acos(f),l),h.multiplyVec3(m,a,e));d.scale(e,((1-c)*g+c*k)/g)},slerpOrLerp:function(a,b,c,e,g){var k=d.length(a),f=d.length(b);d.cross(a,b,l);d.length(l)/k/f>g?(b=Math.acos(d.dot(a,
b)/k/f),h.identity(m),h.rotate(m,c*b,l),h.multiplyVec3(m,a,e),d.scale(e,((1-c)*k+c*f)/k)):d.lerp(a,b,c,e)},angle:function(a,b,c){a=d.normalize(a,q);b=d.normalize(b,r);var e=f.acos(d.dot(a,b));return c&&(a=d.cross(a,b,l),0>d.dot(a,c))?-e:e},clamp:function(a,b,c){return a<b?b:a>c?c:a},isFinite:Number.isFinite||function(a){return"number"===typeof a&&isFinite(a)},isNaN:Number.isNaN||function(a){return a!==a},makePiecewiseLinearFunction:function(a){var b=a.length;return function(c){var e=0;if(c<=a[0][0])return a[0][1];
if(c>=a[b-1][0])return a[b-1][1];for(;c>a[e][0];)e++;var g=a[e][0];c=(g-c)/(g-a[e-1][0]);return c*a[e-1][1]+(1-c)*a[e][1]}},vectorEquals:function(a,b){if(null==a||null==b)return a!==b;if(a.length!==b.length)return!1;for(var c=0;c<a.length;c++)if(a[c]!==b[c])return!1;return!0},floatEqualRelative:function(a,b,c){void 0===c&&(c=1E-6);if(isNaN(a)||isNaN(b))return!1;if(a===b)return!0;var e=Math.abs(a-b),g=Math.abs(a),d=Math.abs(b);if(0===a||0===b||1E-12>g&&1E-12>d){if(e>.01*c)return!1}else if(e/(g+d)>
c)return!1;return!0},floatEqualAbsolute:function(a,b,c){void 0===c&&(c=1E-6);return isNaN(a)||isNaN(b)?!1:(a>b?a-b:b-a)<=c},Cyclical:function(a,b){this.min=a;this.max=b;this.range=b-a;this.ndiff=function(a,e){e=e||0;return Math.ceil((a-e)/this.range)*this.range+e};this._normalize=function(a,e,b,d){d=d||0;b-=d;b<a?b+=this.ndiff(a-b):b>e&&(b-=this.ndiff(b-e));return b+d};this.normalize=function(a,b){return this._normalize(this.min,this.max,a,b)};this.clamp=function(c,e){e=e||0;return f.clamp(c-e,a,
b)+e};this.monotonic=function(a,b,d){return a<b?b:b+this.ndiff(a-b,d)};this.minimalMonotonic=function(a,b,d){return this._normalize(a,a+this.range,b,d)};this.center=function(a,b,d){b=this.monotonic(a,b,d);return this.normalize((a+b)/2,d)};this.diff=function(a,b,d){return this.monotonic(a,b,d)-a};this.contains=function(a,b,d){b=this.minimalMonotonic(a,b);d=this.minimalMonotonic(a,d);return d>a&&d<b}}};f.cyclical2PI=n(new f.Cyclical(0,2*Math.PI));f.cyclicalPI=n(new f.Cyclical(-Math.PI,Math.PI));f.cyclicalDeg=
n(new f.Cyclical(0,360));return f});