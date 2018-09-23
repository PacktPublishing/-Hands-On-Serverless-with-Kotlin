(function (_, Kotlin) {
  'use strict';
  function main$lambda$lambda(closure$res, closure$text) {
    return function () {
      return closure$res.status(200).send('Saved: ' + closure$text);
    };
  }
  function main$lambda(closure$admin) {
    return function (req, res) {
      var text = req.query.text;
      return closure$admin.database().ref('/testMessage').push(text).then(main$lambda$lambda(res, text));
    };
  }
  function main(args) {
    var functions = require('firebase-functions');
    var admin = require('firebase-admin');
    admin.initializeApp(functions.config().firebase);
    exports.saveString = functions.https.onRequest(main$lambda(admin));
  }
  _.main_kand9s$ = main;
  main([]);
  Kotlin.defineModule('index', _);
  return _;
}(module.exports, require('kotlin')));

//# sourceMappingURL=index.js.map
