function iF(){iF=c1c;hF=new H$c}
function oyc(a,b,c,d){var e;a.b.ch(b,c);e=qyc(a.b.j,b,c);ei(e,d,true)}
function jF(d,a){var b=d.b;for(var c in b){b.hasOwnProperty(c)&&a.ue(c)}}
function lF(d,a){a=String(a);var b=d.b;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.Yd(a);return String(c)}
function nF(){iF();var a;a=Akb(hF.pe(zcd),61);if(!a){a=new mF;hF.re(zcd,a)}return a}
function kF(c,b){try{typeof $wnd[b]!='object'&&pF(b);c.b=$wnd[b]}catch(a){pF(b)}}
function pF(a){throw new I_c(I4c+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function mF(){this.c='Dictionary userInfo';kF(this,zcd);if(!this.b){throw new I_c("Cannot find JavaScript object with the name 'userInfo'")}}
function u$b(){var a,b,c,d,e,f,g,i,j,k,n;f=new _Kc;g=new Gvc('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.db.dir=f4c;g.db.style['textAlign']=v4c;YKc(f,new Gvc('<b>This example interacts with the following JavaScript variable:<\/b>'));YKc(f,g);j=new gyc;b=j.k;i=nF();e=(n=new P$c,jF(i,n),n);a=0;for(d=LXc(HM(e.b));d.b.Ae();){c=Akb(RXc(d),1);k=lF(i,c);Zxc(j,0,a,c);oyc(b,0,a,'cw-DictionaryExample-header');Zxc(j,1,a,k);oyc(b,1,a,'cw-DictionaryExample-data');++a}YKc(f,new Gvc('<br><br>'));YKc(f,j);return f}
var zcd='userInfo';YHb(369,1,{61:1},mF);_.Yd=function oF(a){var b;b="Cannot find '"+a+"' in "+this;throw new I_c(b)};_.tS=function qF(){return this.c};_.b=null;_.c=null;var hF;YHb(729,1,Z1c);_.pc=function A$b(){BKb(this.b,u$b())};var oqb=lSc(v8c,R5c,369);M2c(xm)(32);