function cDc(a,b){AA(a.b,b)}
function k5b(a,b){this.c=a;this.b=b}
function n5b(a,b){this.c=a;this.b=b}
function c5b(a,b){hvc(b,'Selected: '+a.ah()+y3c+a.bh())}
function $Lc(b){try{return b.selectionStart}catch(a){return 0}}
function _Lc(b){try{return b.selectionEnd-b.selectionStart}catch(a){return 0}}
function pDc(){kDc();oDc.call(this,sq($doc,'password'),'gwt-PasswordTextBox')}
function b5b(a,b){var c,d;c=new Izc;c.f[R6c]=4;Fzc(c,a);if(b){d=new lvc('Selected: 0, 0');ki(a,new k5b(a,d),(Uw(),Uw(),Tw));ki(a,new n5b(a,d),(iw(),iw(),hw));Fzc(c,d)}return c}
var H9c='read only';FHb(832,1,A1c);_.pc=function i5b(){var a,b,c,d,e,f;iKb(this.b,(a=new HKc,a.f[R6c]=5,b=new nDc,jKc(b.db,p2c,'cwBasicText-textbox'),cDc(b,(Wib(),Wib(),Vib)),c=new nDc,jKc(c.db,p2c,'cwBasicText-textbox-disabled'),c.db[K8c]=H9c,zA(c.b),c.db[A8c]=true,EKc(a,new qvc('<b>Normal text box:<\/b>')),EKc(a,b5b(b,true)),EKc(a,b5b(c,false)),d=new pDc,jKc(d.db,p2c,'cwBasicText-password'),e=new pDc,jKc(e.db,p2c,'cwBasicText-password-disabled'),e.db[K8c]=H9c,zA(e.b),e.db[A8c]=true,EKc(a,new qvc('<br><br><b>Password text box:<\/b>')),EKc(a,b5b(d,true)),EKc(a,b5b(e,false)),f=new vIc,jKc(f.db,p2c,'cwBasicText-textarea'),f.db.rows=5,EKc(a,new qvc('<br><br><b>Text area:<\/b>')),EKc(a,b5b(f,true)),a))};FHb(833,1,i1c,k5b);_.Ic=function l5b(a){c5b(this.c,this.b)};_.b=null;_.c=null;FHb(834,1,x1c,n5b);_.Gc=function o5b(a){c5b(this.c,this.b)};_.b=null;_.c=null;FHb(1174,1057,P0c);_.ah=function fDc(){return $Lc(this.db)};_.bh=function gDc(){return _Lc(this.db)};FHb(1171,1172,P0c,pDc);FHb(1227,1173,P0c);_.ah=function wIc(){return $Lc(this.db)};_.bh=function xIc(){return _Lc(this.db)};var $wb=PRc(M7c,'CwBasicText$2',833),_wb=PRc(M7c,'CwBasicText$3',834),RBb=PRc(G7c,'PasswordTextBox',1171);n2c(wm)(18);