var webpack = require('webpack');
var path = require('path');

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
        loader : 'babel',
        include : [
          SRC_DIR,
          path.resolve(__dirname, 'lib'),
          path.resolve(__dirname, 'node_modules'),
        ],
      },
    ]
  },
  stats: {
    warnings: false,
  },
};

module.exports = config;
