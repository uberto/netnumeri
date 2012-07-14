function nx(){}
function ux(){}
function Nx(){}
function Hi(a,b){ui(b,a)}
function mx(a,b){MPb(b.b,a)}
function tx(a,b){NPb(b.b,a)}
function Mx(a,b){OPb(b.b,a)}
function Dpb(a){this.b=a}
function Kpb(a){this.b=a}
function eQb(a){this.b=a}
function xQb(a){this.b=a}
function UPb(a){a.g=false;SIb(a.db)}
function WPb(){XPb.call(this,new vQb)}
function MPb(a,b){SPb(a,(a.b,sw(b)),tw(b))}
function NPb(a,b){TPb(a,(a.b,sw(b)),tw(b))}
function OPb(a,b){UPb(a,(a.b,sw(b),tw(b)))}
function VPb(a){!a.i&&(a.i=CJb(new eQb(a)));jj(a)}
function lx(){lx=Slc;kx=new Gw(Jqc,new nx)}
function sx(){sx=Slc;rx=new Gw(crc,new ux)}
function Lx(){Lx=Slc;Kx=new Gw(frc,new Nx)}
function vQb(){sQb.call(this);this.db[Fnc]='Caption'}
function PPb(a){if(a.i){lac(a.i.b);a.i=null}Zi(a,false)}
function SPb(a,b,c){if(!MIb){a.g=true;TIb(a.db);a.e=b;a.f=c}}
function FPb(a,b){var c,d;d=gKb(a.c,b);c=gKb(d,1);return Pq(c)}
function hMb(a,b,c){var d;d=gMb(a,b);!!d&&(d[Drc]=c.b,undefined)}
function RPb(a,b){r3b(a.db,Dnc,b);Mh(a.b,b+'-caption');r3b(FPb(a.k,1),b,duc)}
function QPb(a,b){var c;c=ir(b);if(Mq(c)){return Zq(Rq(FPb(a.k,0)),c)}return false}
function TPb(a,b,c){var d,e;if(a.g){d=b+_q(a.db);e=c+ar(a.db);if(d<a.c||d>=a.j||e<a.d){return}ej(a,d-a.e,e-a.f)}}
function sw(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientX||0)-_q(b)+br(b)+br(b.ownerDocument.body)}return a.b.clientX||0}
function tw(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientY||0)-ar(b)+(b.scrollTop||0)+(b.ownerDocument.body.scrollTop||0)}return a.b.clientY||0}
function dj(a){a.x=true;if(!a.t){a.t=$doc.createElement(Lnc);a.t.className='gwt-PopupPanelGlass';a.t.style[joc]=(Qt(),moc);a.t.style[ipc]=0+(Nu(),Boc);a.t.style[jpc]=kpc}}
function XPb(a){var b,c;aPb.call(this,false,true,suc);si(a);this.b=a;c=FPb(this.k,0);NIb(c,this.b.db);Hi(this,this.b);Rq(Pq(this.db))[Fnc]='gwt-DialogBox';this.j=lr($doc);this.c=0;this.d=0;b=new xQb(this);li(this,b,(lx(),lx(),kx));li(this,b,(Lx(),Lx(),Kx));li(this,b,(sx(),sx(),rx));li(this,b,(Fx(),Fx(),Ex));li(this,b,(zx(),zx(),yx))}
function zpb(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new WPb,RPb(g,'cwDialogBox'),lQb(g.b,'Sample DialogBox'),i=new P3b,i.f[Frc]=4,Mi(g.k,i),$i(g),j=new uQb('This is an example of a standard dialog box component.'),M3b(i,j),hMb(i,j,(bUb(),XTb)),k=new WHb((x5(),m5)),M3b(i,k),hMb(i,k,XTb),n=new eMb(Vtc,new Kpb(g)),M3b(i,n),gD(),hMb(i,n,aUb),g);dj(a);a.w=true;e=new eMb('Show Dialog Box',new Dpb(a));d=new uQb('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new KVb;c.db.size=1;for(b=10;b>0;--b){GVb(c,ruc+b,ruc+b,-1)}f=new P3b;f.f[Frc]=8;M3b(f,e);M3b(f,d);M3b(f,c);return f}
var ruc='item ';M0(294,282,{},nx);_.Ec=function ox(a){mx(this,VG(a,38))};_.Hc=function px(){return kx};var kx;M0(295,282,{},ux);_.Ec=function vx(a){tx(this,VG(a,39))};_.Hc=function wx(){return rx};var rx;M0(298,282,{},Nx);_.Ec=function Ox(a){Mx(this,VG(a,42))};_.Hc=function Px(){return Kx};var Kx;M0(739,1,Kmc,Dpb);_.Kc=function Epb(a){Wi(this.b);VPb(this.b)};_.b=null;M0(740,1,Nmc);_.pc=function Ipb(){p3(this.b,zpb())};M0(741,1,Kmc,Kpb);_.Kc=function Lpb(a){PPb(this.b)};_.b=null;M0(1002,998,cmc,WPb);_.Pb=function YPb(){try{pi(this.k)}finally{pi(this.b)}};_.Qb=function ZPb(){try{ri(this.k)}finally{ri(this.b)}};_.dc=function $Pb(){PPb(this)};_.Ub=function _Pb(a){switch(WJb(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.g&&!QPb(this,a)){return}}qi(this,a)};_.Ib=function aQb(a){RPb(this,a)};_.ec=function bQb(a){var b;b=a.e;!a.b&&WJb(a.e.type)==4&&QPb(this,b)&&(b.preventDefault(),undefined);a.d&&(a.e,false)&&(a.b=true)};_.fc=function cQb(){VPb(this)};_.b=null;_.c=0;_.d=0;_.e=0;_.f=0;_.g=false;_.i=null;_.j=0;M0(1003,1,Vmc,eQb);_.Rc=function fQb(a){this.b.j=a.b};_.b=null;M0(1004,1005,amc,vQb);M0(1008,1,{38:1,39:1,40:1,41:1,42:1,54:1},xQb);_.Nc=function yQb(a){};_.Oc=function zQb(a){};_.b=null;var UR=_ac(ysc,'CwDialogBox$1',739),WR=_ac(ysc,'CwDialogBox$3',741),EV=_ac(usc,'DialogBox',1002),CV=_ac(usc,'DialogBox$CaptionImpl',1004),DV=_ac(usc,'DialogBox$MouseHandler',1008),BV=_ac(usc,'DialogBox$1',1003),yL=_ac(Usc,'MouseDownEvent',294),DL=_ac(Usc,'MouseUpEvent',298),AL=_ac(Usc,'MouseMoveEvent',295);Anc(xm)(15);