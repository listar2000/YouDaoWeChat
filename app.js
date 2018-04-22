//app.js
App({
  getExpressInfo: function (nu, cb) {
    wx.request({
      url: 'http://localhost:8080/YD/youDaoServlet?word=' + nu,
      data: {
        x: '',
        y: ''
      },
      success: function(res) {
        console.log(res.data);
        cb(res.data);
      }
    })
  },
  globalData: {
    userInfo: null
  }
})