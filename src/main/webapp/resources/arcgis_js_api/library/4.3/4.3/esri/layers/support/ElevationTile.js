// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["require","exports"],function(c,d){function m(a,l,b){var e=a.safeWidth,h=a.width,f=a.values,g=a.noDataValue;b=a.dy*(a.y1-b);b=0>b?0:b>e?e:b;a=a.dx*(l-a.x0);a=0>a?0:a>e?e:a;e=Math.floor(b);l=Math.floor(a);var k=e*h+l,d=k+h,c=f[k],h=f[d],k=f[k+1],f=f[d+1];if(c!==g&&h!==g&&k!==g&&f!==g)return g=a-l,a=c+(k-c)*g,a+(h+(f-h)*g-a)*(b-e)}c=function(){function a(a,b){this.tile=a;this.update(b)}a.prototype.update=function(a){if(a){var b=this.tile.extent;this.samplerData={values:a.values,width:a.width,
height:a.height,safeWidth:.99999999*(a.width-1),noDataValue:a.noDataValue,dx:(a.width-1)/(b[2]-b[0]),dy:(a.width-1)/(b[3]-b[1]),x0:b[0],y1:b[3]}}else this.samplerData=null};a.prototype.sample=function(a,b){if(this.samplerData)return m(this.samplerData,a,b)};return a}();d.ElevationTile=c;d.sample=m;Object.defineProperty(d,"__esModule",{value:!0});d.default=c});