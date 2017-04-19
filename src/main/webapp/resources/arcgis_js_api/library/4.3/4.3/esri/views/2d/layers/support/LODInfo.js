// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["require","exports","./TileKey","../../../../geometry/support/spatialReferenceUtils"],function(r,t,d,p){function m(c,a,b){c[0]=a;c[1]=b;return c}return function(){function c(a,b,c,e,f,g,k,l,q,n,d,m){this.level=a;this.resolution=b;this.scale=c;this.origin=e;this.first=f;this.last=g;this.size=k;this.norm=l;this.worldStart=q;this.worldEnd=n;this.worldSize=d;this.wrap=m}c.create=function(a,b,h){var e=p.getInfo(a.spatialReference),f=[a.origin.x,a.origin.y],g=[a.size[0]*b.resolution,a.size[1]*b.resolution],
k=[-Infinity,-Infinity],l=[Infinity,Infinity],d=[Infinity,Infinity];h&&(m(k,Math.max(0,Math.floor((h.xmin-f[0])/g[0])),Math.max(0,Math.floor((f[1]-h.ymax)/g[1]))),m(l,Math.max(0,Math.floor((h.xmax-f[0])/g[0])),Math.max(0,Math.floor((f[1]-h.ymin)/g[1]))),m(d,l[0]-k[0]+1,l[1]-k[1]+1));var n;e?(a=[Math.ceil(Math.round(2*e.origin[1]/b.resolution)/a.size[0]),d[1]],e=[Math.floor((e.origin[0]-f[0])/g[0]),k[1]],h=[a[0]-e[0]-1,l[1]],n=!0):(e=k,h=l,a=d,n=!1);return new c(b.level,b.resolution,b.scale,f,k,l,
d,g,e,h,a,n)};c.prototype.normalizeCol=function(a){if(!this.wrap)return a;var b=this.worldSize[0];return 0>a?b-1-Math.abs((a+1)%b):a%b};c.prototype.denormalizeCol=function(a,b){return this.wrap?this.worldSize[0]*b+a:a};c.prototype.getWorldForColumn=function(a){return this.wrap?Math.floor(a/this.worldSize[0]):0};c.prototype.getFirstColumnForWorld=function(a){return a*this.worldSize[0]+this.first[0]};c.prototype.getLastColumnForWorld=function(a){return a*this.worldSize[0]+this.first[0]+this.size[0]-
1};c.prototype.getColumnForX=function(a){return(a-this.origin[0])/this.norm[0]};c.prototype.getXForColumn=function(a){return this.origin[0]+a*this.norm[0]};c.prototype.getRowForY=function(a){return(this.origin[1]-a)/this.norm[1]};c.prototype.getYForRow=function(a){return this.origin[1]-a*this.norm[1]};c.prototype.getTileBounds=function(a,b){b=d.pool.acquire(b);var c=this.getXForColumn(this.denormalizeCol(b.col,b.world)),e=this.getYForRow(b.row+1),f=this.getXForColumn(this.denormalizeCol(b.col,b.world)+
1),g=this.getYForRow(b.row);a[0]=c;a[1]=e;a[2]=f;a[3]=g;d.pool.release(b);return a};c.prototype.getTileCoords=function(a,b){b=d.pool.acquire(b);m(a,this.getXForColumn(this.denormalizeCol(b.col,b.world)),this.getYForRow(b.row));d.pool.release(b);return a};return c}()});