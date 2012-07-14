function sC(){sC=zlc;rC=new cjc}
function tC(d,a){var b=d.a;for(var c in b){b.hasOwnProperty(c)&&a.pd(c)}}
function RSb(a,b,c,d){var e;a.a.Zf(b,c);e=TSb(a.a.i,b,c);Th(e,d,true)}
function xC(){sC();var a;a=yG(rC.kd(iwc),60);if(!a){a=new wC;rC.md(iwc,a)}return a}
function uC(c,b){try{typeof $wnd[b]!='object'&&zC(b);c.a=$wnd[b]}catch(a){zC(b)}}
function zC(a){throw new dkc(Voc+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function vC(d,a){a=String(a);var b=d.a;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.fd(a);return String(c)}
function wC(){this.b='Dictionary userInfo';uC(this,iwc);if(!this.a){throw new dkc("Cannot find JavaScript object with the name 'userInfo'")}}
function Kib(){var a,b,c,d,e,f,g,i,j,k,n;f=new D3b;g=new hQb('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.cb.dir=qoc;g.cb.style['textAlign']=Joc;A3b(f,new hQb('<b>This example interacts with the following JavaScript variable:<\/b>'));A3b(f,g);j=new JSb;b=j.j;i=xC();e=(n=new kjc,tC(i,n),n);a=0;for(d=ggc(yD(e.a));d.a.vd();){c=yG(mgc(d),1);k=vC(i,c);ASb(j,0,a,c);RSb(b,0,a,'cw-DictionaryExample-header');ASb(j,1,a,k);RSb(b,1,a,'cw-DictionaryExample-data');++a}A3b(f,new hQb('<br><br>'));A3b(f,j);return f}
var iwc='userInfo';h0(342,1,{60:1},wC);_.fd=function yC(a){var b;b="Cannot find '"+a+"' in "+this;throw new dkc(b)};_.tS=function AC(){return this.b};_.a=null;_.b=null;var rC;h0(639,1,tmc);_.kc=function Qib(){R2(this.a,Kib())};var IL=Jac(isc,Fpc,342);gnc(jm)(32);