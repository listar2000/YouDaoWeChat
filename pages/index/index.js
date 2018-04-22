//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    inputInfo: null,
    expressInfo: null
  },

  onShareAppMessage: function(e) {
    return{
      title: "转发有好运",
      path: '../logs/logs',
    }
  },

  input: function(e) {
    this.setData({inputInfo: e.detail.value});
    // console.log(e.detail.value);
  },

  btnClick: function(e) {
    var thisExpress = this;
    app.getExpressInfo(this.data.inputInfo, function(data) {
      console.log(data);
      thisExpress.setData({expressInfo: data});
    }) 
  }
})

