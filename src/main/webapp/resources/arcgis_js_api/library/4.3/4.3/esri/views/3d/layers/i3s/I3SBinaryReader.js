// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define("require exports dojo/_base/lang ../../../../core/Logger ../../../../core/Error ./LEPCC".split(" "),function(B,h,n,z,l,u){function A(b,a,d){for(var f="",g=0;g<d;){var e=b[a+g];if(128>e)f+=String.fromCharCode(e),g++;else if(192<=e&&224>e){if(g+1>=d)throw new l("utf8-decode-error","UTF-8 Decode failed. Two byte character was truncated.");var c=b[a+g+1],e=(e&31)<<6|c&63,f=f+String.fromCharCode(e),g=g+2}else if(224<=e&&240>e){if(g+2>=d)throw new l("utf8-decode-error","UTF-8 Decode failed. Multi byte character was truncated.");
var c=b[a+g+1],k=b[a+g+2],e=(e&15)<<12|(c&63)<<6|k&63,f=f+String.fromCharCode(e),g=g+3}else if(240<=e&&248>e){if(g+3>=d)throw new l("utf8-decode-error","UTF-8 Decode failed. Multi byte character was truncated.");c=b[a+g+1];k=b[a+g+2];e=(e&7)<<18|(c&63)<<12|(k&63)<<6|b[a+g+3]&63;f=65536<=e?f+String.fromCharCode((e-65536>>10)+55296,(e&1023)+56320):f+String.fromCharCode(e);g+=4}else throw new l("utf8-decode-error","UTF-8 Decode failed. Invalid multi byte sequence.");}return f}function p(b,a){for(var d=
{byteOffset:0,byteCount:0,fields:Object.create(null)},f=0,g=0;g<a.length;g++){var e=a[g],c=e.valueType||e.type;d.fields[e.property]=(0,h.valueType2ArrayBufferReader[c])(b,f);f+=h.valueType2TypedArrayClassMap[c].BYTES_PER_ELEMENT}d.byteCount=f;return d}function v(b,a,d){var f=[],g,e=0,c;for(c=0;c<b;c+=1){g=a[c];if(0<g){if(f.push(A(d,e,g-1)),0!==d[e+g-1])throw new l("string-array-error","Invalid string array: missing null termination.");}else f.push(null);e+=g}return f}function q(b,a){return new h.valueType2TypedArrayClassMap[a.valueType](b,
a.byteOffset,a.count*a.valuesPerElement)}function w(b,a){return new Uint8Array(b,a.byteOffset,a.byteCount)}function x(b,a,d){b=null!=a.header?p(b,a.header):{byteOffset:0,byteCount:0,fields:{count:d}};d={header:b,byteOffset:b.byteCount,byteCount:0,entries:Object.create(null)};for(var f=b.byteCount,g=0;g<a.ordering.length;g++){var e=a.ordering[g],c=n.clone(a[e]);c.count=b.fields.count;if("String"===c.valueType){if(c.byteOffset=f,c.byteCount=b.fields[e+"ByteCount"],"UTF-8"!==c.encoding)throw new l("unsupported-encoding",
"Unsupported String encoding.",{encoding:c.encoding});}else if(r(c.valueType)){var k=m(c.valueType),f=f+(0!==f%k?k-f%k:0);c.byteOffset=f;c.byteCount=k*c.valuesPerElement*c.count}else throw new l("unsupported-value-type","Unsupported binary valueType",{valueType:c.valueType});f+=c.byteCount;d.entries[e]=c}d.byteCount=f-d.byteOffset;return d}function y(b,a,d){a!==b&&t.error("Invalid "+d+" buffer size\n expected: "+b+", actual: "+a+")");if(a<b)throw new l("buffer-too-small","Binary buffer is too small",
{expectedSize:b,actualSize:a});}function r(b){return h.valueType2TypedArrayClassMap.hasOwnProperty(b)}function m(b){return r(b)&&h.valueType2TypedArrayClassMap[b].BYTES_PER_ELEMENT}var t=z.getLogger("esri.layers.SceneService");h.readHeader=p;h.readStringArray=v;h.createTypedView=q;h.createRawView=w;h.createAttributeDataIndex=x;h.createGeometryDataIndex=function(b,a,d){var f=p(b,a.header),g=f.byteCount,e={header:f,byteOffset:f.byteCount,byteCount:0,vertexAttributes:n.clone(a.vertexAttributes)},c=e.vertexAttributes;
d||null==c.region||delete c.region;var k=f.fields.vertexCount||f.fields.count;for(d=0;d<a.ordering.length;d++){var h=a.ordering[d];null!=c[h]&&(c[h].byteOffset=g,c[h].count=k,g+=m(c[h].valueType)*c[h].valuesPerElement*k)}c=f.fields.faceCount;if(a.faces&&c)for(e.faces=n.clone(a.faces),k=e.faces,d=0;d<a.ordering.length;d++)h=a.ordering[d],null!=k[h]&&(k[h].byteOffset=g,k[h].count=c,g+=m(k[h].valueType)*k[h].valuesPerElement*c);f=f.fields.featureCount;if(a.featureAttributes&&a.featureAttributeOrder&&
f)for(e.featureAttributes=n.clone(a.featureAttributes),c=e.featureAttributes,d=0;d<a.featureAttributeOrder.length;d++)k=a.featureAttributeOrder[d],c[k].byteOffset=g,c[k].count=f,h=m(c[k].valueType),"UInt64"===c[k].valueType&&(h=8),g+=h*c[k].valuesPerElement*f;y(g,b.byteLength,"geometry");e.byteCount=g-e.byteOffset;return e};h.readBinaryAttribute=function(b,a,d){if("lepcc-rgb"===b.encoding)return u.decodeRGB(a);if("lepcc-intensity"===b.encoding)return u.decodeIntensity(a);if(null!=b.encoding&&""!==
b.encoding)throw new l("unknown-attribute-storage-info-encoding","Unknown Attribute Storage Info Encoding");b["attributeByteCounts "]&&!b.attributeByteCounts&&(t.error("Warning: Trailing space in 'attributeByteCounts '."),b.attributeByteCounts=b["attributeByteCounts "]);"ObjectIds"===b.ordering[0]&&b.hasOwnProperty("objectIds")&&(t.error("Warning: Case error in objectIds"),b.ordering[0]="objectIds");d=x(a,b,d);y(d.byteOffset+d.byteCount,a.byteLength,"attribute");if(b=d.entries.attributeValues||d.entries.objectIds){if("String"===
b.valueType){d=d.entries.attributeByteCounts;var f=q(a,d);a=w(a,b);return v(d.count,f,a)}return q(a,b)}throw new l("bad-attribute-storage-info","Bad attributeStorageInfo specification.");};h.valueType2TypedArrayClassMap={Float32:Float32Array,Float64:Float64Array,UInt8:Uint8Array,Int8:Int8Array,UInt16:Uint16Array,Int16:Int16Array,UInt32:Uint32Array,Int32:Int32Array};h.valueType2ArrayBufferReader={Float32:function(b,a){return(new DataView(b,0)).getFloat32(a,!0)},Float64:function(b,a){return(new DataView(b,
0)).getFloat64(a,!0)},UInt8:function(b,a){return(new DataView(b,0)).getUint8(a)},Int8:function(b,a){return(new DataView(b,0)).getInt8(a)},UInt16:function(b,a){return(new DataView(b,0)).getUint16(a,!0)},Int16:function(b,a){return(new DataView(b,0)).getInt16(a,!0)},UInt32:function(b,a){return(new DataView(b,0)).getUint32(a,!0)},Int32:function(b,a){return(new DataView(b,0)).getInt32(a,!0)}};h.isValueType=r;h.getBytesPerValue=m});