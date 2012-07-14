function wE(){wE=P_c;vE=new sZc}
function txc(a,b,c,d){var e;a.b.Wg(b,c);e=vxc(a.b.j,b,c);Uh(e,d,true)}
function xE(d,a){var b=d.b;for(var c in b){b.hasOwnProperty(c)&&a.me(c)}}
function zE(d,a){a=String(a);var b=d.b;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.Qd(a);return String(c)}
function BE(){wE();var a;a=Ojb(vE.he(dbd),61);if(!a){a=new AE;vE.je(dbd,a)}return a}
function yE(c,b){try{typeof $wnd[b]!='object'&&DE(b);c.b=$wnd[b]}catch(a){DE(b)}}
function DE(a){throw new t$c(l3c+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function AE(){this.c='Dictionary userInfo';yE(this,dbd);if(!this.b){throw new t$c("Cannot find JavaScript object with the name 'userInfo'")}}
function zZb(){var a,b,c,d,e,f,g,i,j,k,n;f=new aKc;g=new Huc('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.db.dir=G2c;g.db.style['textAlign']=$2c;ZJc(f,new Huc('<b>This example interacts with the following JavaScript variable:<\/b>'));ZJc(f,g);j=new lxc;b=j.k;i=BE();e=(n=new AZc,xE(i,n),n);a=0;for(d=wWc(UL(e.b));d.b.se();){c=Ojb(CWc(d),1);k=zE(i,c);cxc(j,0,a,c);txc(b,0,a,'cw-DictionaryExample-header');cxc(j,1,a,k);txc(b,1,a,'cw-DictionaryExample-data');++a}ZJc(f,new Huc('<br><br>'));ZJc(f,j);return f}
var dbd='userInfo';bHb(364,1,{61:1},AE);_.Qd=function CE(a){var b;b="Cannot find '"+a+"' in "+this;throw new t$c(b)};_.tS=function EE(){return this.c};_.b=null;_.c=null;var vE;bHb(724,1,J0c);_.lc=function FZb(){GJb(this.b,zZb())};var wpb=ZQc($6c,u4c,364);w1c(km)(32);