function PC(){PC=Slc;OC=new vjc}
function cTb(a,b,c,d){var e;a.b.fg(b,c);e=eTb(a.b.j,b,c);ei(e,d,true)}
function QC(d,a){var b=d.b;for(var c in b){b.hasOwnProperty(c)&&a.xd(c)}}
function SC(d,a){a=String(a);var b=d.b;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.od(a);return String(c)}
function UC(){PC();var a;a=VG(OC.sd(Kwc),60);if(!a){a=new TC;OC.ud(Kwc,a)}return a}
function RC(c,b){try{typeof $wnd[b]!='object'&&WC(b);c.b=$wnd[b]}catch(a){WC(b)}}
function WC(a){throw new wkc(vpc+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function TC(){this.c='Dictionary userInfo';RC(this,Kwc);if(!this.b){throw new wkc("Cannot find JavaScript object with the name 'userInfo'")}}
function ijb(){var a,b,c,d,e,f,g,i,j,k,n;f=new P3b;g=new uQb('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.db.dir=Voc;g.db.style['textAlign']=ipc;M3b(f,new uQb('<b>This example interacts with the following JavaScript variable:<\/b>'));M3b(f,g);j=new WSb;b=j.k;i=UC();e=(n=new Djc,QC(i,n),n);a=0;for(d=zgc(VD(e.b));d.b.Dd();){c=VG(Fgc(d),1);k=SC(i,c);NSb(j,0,a,c);cTb(b,0,a,'cw-DictionaryExample-header');NSb(j,1,a,k);cTb(b,1,a,'cw-DictionaryExample-data');++a}M3b(f,new uQb('<br><br>'));M3b(f,j);return f}
var Kwc='userInfo';M0(346,1,{60:1},TC);_.od=function VC(a){var b;b="Cannot find '"+a+"' in "+this;throw new wkc(b)};_.tS=function XC(){return this.c};_.b=null;_.c=null;var OC;M0(642,1,Nmc);_.pc=function ojb(){p3(this.b,ijb())};var lM=_ac(Lsc,fqc,346);Anc(xm)(32);