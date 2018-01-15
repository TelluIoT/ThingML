var webpack = require('webpack');
var path = require('path');

var LIB_DIR = path.resolve(__dirname, 'lib');
var SRC_DIR = path.resolve(__dirname, 'src');
var BUILD_DIR = path.resolve(__dirname, 'build');

var config = {
  entry: SRC_DIR + '/main.js',
  output: {
    path: BUILD_DIR,
    filename: 'bundle.js'
  },
  module : {
    loaders : [
      {
        test : /\.js?/,
        include : LIB_DIR,
        loader : 'babel'
      },
      {
        test : /\.js?/,
        include : SRC_DIR,
        loader : 'babel'
      }
    ]
  }
};

module.exports = config;