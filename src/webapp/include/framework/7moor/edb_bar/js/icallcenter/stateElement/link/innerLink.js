hojo.provide("icallcenter.stateElement.link.innerLink");hojo.declare("icallcenter.stateElement.link.innerLink",null,{constructor:function(a){this._base=a},_base:null,_callState:"stInnerTalking",_changeToolBarState:function(b){hojo.publish("EvtCallToolBarChange",[b._callState])},_switchCallState:function(c){if(c.Event=="ChannelStatus"){if(c.Exten==this._base._phone.sipNo){if(c.ChannelStatus=="Hangup"){this._base._curCallState=this._base._getInvalid();this._changeToolBarState(this._base._curCallState)}else {if(c.ChannelStatus=="Link"){if(c.LinkedChannel.ChannelType=="consultation"){this._base._curCallState=this._base._getConsultationLink();this._changeToolBarState(this._base._curCallState)};if(c.LinkedChannel.ChannelType=="transfer"){}}else {if(c.ChannelStatus=="hold"){this._base._curCallState=this._base._getHold();this._changeToolBarState(this._base._curCallState)}}}}}},_publish:function(){}})