function ax(){}
function hx(){}
function Ax(){}
function Gi(a,b){ti(b,a)}
function _w(a,b){wPb(b.b,a)}
function gx(a,b){xPb(b.b,a)}
function zx(a,b){yPb(b.b,a)}
function kpb(a){this.b=a}
function rpb(a){this.b=a}
function QPb(a){this.b=a}
function hQb(a){this.b=a}
function EPb(a){a.g=false;CIb(a.db)}
function wPb(a,b){CPb(a,(a.b,fw(b)),gw(b))}
function xPb(a,b){DPb(a,(a.b,fw(b)),gw(b))}
function yPb(a,b){EPb(a,(a.b,fw(b),gw(b)))}
function GPb(){Ui();HPb.call(this,new fQb)}
function $w(){$w=tlc;Zw=new tw(cqc,new ax)}
function fx(){fx=tlc;ex=new tw(zqc,new hx)}
function yx(){yx=tlc;xx=new tw(Cqc,new Ax)}
function FPb(a){!a.i&&(a.i=lJb(new QPb(a)));jj(a)}
function zPb(a){if(a.i){P9b(a.i.b);a.i=null}Zi(a,false)}
function fQb(){cQb.call(this);this.db[fnc]='Caption'}
function CPb(a,b,c){if(!wIb){a.g=true;DIb(a.db);a.e=b;a.f=c}}
function pPb(a,b){var c,d;d=RJb(a.c,b);c=RJb(d,1);return uq(c)}
function TLb(a,b,c){var d;d=SLb(a,b);!!d&&(d[drc]=c.b,undefined)}
function BPb(a,b){Z2b(a.db,dnc,b);Lh(a.b,b+'-caption');Z2b(pPb(a.k,1),b,Gtc)}
function APb(a,b){var c;c=b.target;if(rq(c)){return Oq(wq(pPb(a.k,0)),c)}return false}
function DPb(a,b,c){var d,e;if(a.g){d=b+gq(a.db);e=c+hq(a.db);if(d<a.c||d>=a.j||e<a.d){return}ej(a,d-a.e,e-a.f)}}
function fw(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientX||0)-Gq(b)+Mq(b)+$q(b.ownerDocument)}return a.b.clientX||0}
function gw(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientY||0)-Iq(b)+(b.scrollTop||0)+_q(b.ownerDocument)}return a.b.clientY||0}
function dj(a){a.x=true;if(!a.t){a.t=$doc.createElement(lnc);a.t.className='gwt-PopupPanelGlass';a.t.style[Aoc]=(Dt(),Boc);a.t.style[Doc]=0+(Au(),Rnc);a.t.style[Eoc]=Foc}}
function HPb(a){var b,c;MOb.call(this,false,true,Vtc);ri(a);this.b=a;c=pPb(this.k,0);xIb(c,this.b.db);Gi(this,this.b);q4b(uq(this.db))[fnc]='gwt-DialogBox';this.j=Xq($doc);this.c=Kq($doc);this.d=Lq($doc);b=new hQb(this);ki(this,b,($w(),$w(),Zw));ki(this,b,(yx(),yx(),xx));ki(this,b,(fx(),fx(),ex));ki(this,b,(sx(),sx(),rx));ki(this,b,(mx(),mx(),lx))}
function gpb(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new GPb,BPb(g,'cwDialogBox'),XPb(g.b,'Sample DialogBox'),i=new v3b,i.f[frc]=4,Li(g.k,i),$i(g),j=new eQb('This is an example of a standard dialog box component.'),s3b(i,j),TLb(i,j,(LTb(),FTb)),k=new GHb((e5(),V4)),s3b(i,k),TLb(i,k,FTb),n=new QLb(wtc,new rpb(g)),s3b(i,n),VC(),TLb(i,n,KTb),g);dj(a);a.w=true;e=new QLb('Show Dialog Box',new kpb(a));d=new eQb('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new rVb;c.db.size=1;for(b=10;b>0;--b){nVb(c,Utc+b,Utc+b,-1)}f=new v3b;f.f[frc]=8;s3b(f,e);s3b(f,d);s3b(f,c);return f}
var Utc='item ';t0(290,278,{},ax);_.Ac=function bx(a){_w(this,IG(a,38))};_.Dc=function cx(){return Zw};var Zw;t0(291,278,{},hx);_.Ac=function ix(a){gx(this,IG(a,39))};_.Dc=function jx(){return ex};var ex;t0(294,278,{},Ax);_.Ac=function Bx(a){zx(this,IG(a,42))};_.Dc=function Cx(){return xx};var xx;t0(735,1,lmc,kpb);_.Gc=function lpb(a){Wi(this.b);FPb(this.b)};_.b=null;t0(736,1,omc);_.pc=function ppb(){Y2(this.b,gpb())};t0(737,1,lmc,rpb);_.Gc=function spb(a){zPb(this.b)};_.b=null;t0(1000,996,Flc,GPb);_.Pb=function IPb(){try{oi(this.k)}finally{oi(this.b)}};_.Qb=function JPb(){try{qi(this.k)}finally{qi(this.b)}};_.dc=function KPb(){zPb(this)};_.Ub=function LPb(a){switch(FJb(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.g&&!APb(this,a)){return}}pi(this,a)};_.Ib=function MPb(a){BPb(this,a)};_.ec=function NPb(a){var b;b=a.e;!a.b&&FJb(a.e.type)==4&&APb(this,b)&&(b.preventDefault(),undefined);a.d&&(a.e,false)&&(a.b=true)};_.fc=function OPb(){FPb(this)};_.b=null;_.c=0;_.d=0;_.e=0;_.f=0;_.g=false;_.i=null;_.j=0;t0(1001,1,wmc,QPb);_.Nc=function RPb(a){this.b.j=a.b};_.b=null;t0(1002,1003,Dlc,fQb);t0(1006,1,{38:1,39:1,40:1,41:1,42:1,54:1},hQb);_.Jc=function iQb(a){};_.Kc=function jQb(a){};_.b=null;var DR=Dac($rc,'CwDialogBox$1',735),FR=Dac($rc,'CwDialogBox$3',737),nV=Dac(Wrc,'DialogBox',1000),lV=Dac(Wrc,'DialogBox$CaptionImpl',1002),mV=Dac(Wrc,'DialogBox$MouseHandler',1006),kV=Dac(Wrc,'DialogBox$1',1001),hL=Dac(usc,'MouseDownEvent',290),mL=Dac(usc,'MouseUpEvent',294),jL=Dac(usc,'MouseMoveEvent',291);bnc(wm)(15);