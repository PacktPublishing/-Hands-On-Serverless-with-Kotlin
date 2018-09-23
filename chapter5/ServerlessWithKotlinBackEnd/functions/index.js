(function (_, Kotlin) {
  'use strict';
  var toString = Kotlin.toString;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  function main$lambda(req, res) {
    var text = req.query.text;
    return res.status(200).send('Echo : ' + text);
  }
  function main$lambda$lambda(closure$text, closure$res) {
    return function () {
      console.log('Your text is ' + closure$text);
      return closure$res.status(200).send('Saved: ' + closure$text);
    };
  }
  function main$lambda_0(closure$admin) {
    return function (req, res) {
      var text = req.query.text;
      return closure$admin.database().ref('/testMessage').push(text).then(main$lambda$lambda(text, res));
    };
  }
  function main$lambda$lambda_0(closure$result) {
    return function (snapShot, prevChildKey) {
      console.log('Data : ' + toString(snapShot.val()));
      return closure$result.add_11rb$(snapShot.val());
    };
  }
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function main$lambda_1(closure$admin) {
    return function (req, res) {
      var result = ArrayList_init();
      var ref = closure$admin.database().ref('/testMessage');
      ref.on('child_added', main$lambda$lambda_0(result));
      return res.status(200).send(JSON.stringify(result));
    };
  }
  function main$lambda$lambda_1(closure$res, closure$newPoll) {
    return function () {
      return closure$res.status(200).send(JSON.stringify(closure$newPoll));
    };
  }
  function main$lambda_2(closure$admin) {
    return function (req, res) {
      var reqBody = req.rawBody;
      var poll = JSON.parse(reqBody);
      var newPoll = new ResponsePoll(getRandomPollId().toString(), poll.pollQuestion, poll.pollOptions);
      res.status(200).send(JSON.stringify(newPoll));
      var ref = closure$admin.database().ref('/testPoll');
      return ref.push(newPoll).then(main$lambda$lambda_1(res, newPoll));
    };
  }
  function main$lambda$lambda_2(closure$result) {
    return function (snapShot, prevChildKey) {
      console.log('Data : ' + toString(snapShot.val()));
      return closure$result.add_11rb$(snapShot.val());
    };
  }
  function main$lambda_3(closure$admin) {
    return function (req, res) {
      var result = ArrayList_init();
      var ref = closure$admin.database().ref('/testPoll');
      ref.on('child_added', main$lambda$lambda_2(result));
      return res.status(200).send(JSON.stringify(result));
    };
  }
  function main(args) {
    var functions = require('firebase-functions');
    var admin = require('firebase-admin');
    admin.initializeApp(functions.config().firebase);
    exports.echoString = functions.https.onRequest(main$lambda);
    exports.saveString = functions.https.onRequest(main$lambda_0(admin));
    exports.getStrings = functions.https.onRequest(main$lambda_1(admin));
    exports.createPoll = functions.https.onRequest(main$lambda_2(admin));
    exports.getAllPoll = functions.https.onRequest(main$lambda_3(admin));
  }
  function User(userId, userName, fullName, emailAddress, imgUrl) {
    this.userId = userId;
    this.userName = userName;
    this.fullName = fullName;
    this.emailAddress = emailAddress;
    this.imgUrl = imgUrl;
  }
  User.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'User',
    interfaces: []
  };
  User.prototype.component1 = function () {
    return this.userId;
  };
  User.prototype.component2 = function () {
    return this.userName;
  };
  User.prototype.component3 = function () {
    return this.fullName;
  };
  User.prototype.component4 = function () {
    return this.emailAddress;
  };
  User.prototype.component5 = function () {
    return this.imgUrl;
  };
  User.prototype.copy_st73ev$ = function (userId, userName, fullName, emailAddress, imgUrl) {
    return new User(userId === void 0 ? this.userId : userId, userName === void 0 ? this.userName : userName, fullName === void 0 ? this.fullName : fullName, emailAddress === void 0 ? this.emailAddress : emailAddress, imgUrl === void 0 ? this.imgUrl : imgUrl);
  };
  User.prototype.toString = function () {
    return 'User(userId=' + Kotlin.toString(this.userId) + (', userName=' + Kotlin.toString(this.userName)) + (', fullName=' + Kotlin.toString(this.fullName)) + (', emailAddress=' + Kotlin.toString(this.emailAddress)) + (', imgUrl=' + Kotlin.toString(this.imgUrl)) + ')';
  };
  User.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.userId) | 0;
    result = result * 31 + Kotlin.hashCode(this.userName) | 0;
    result = result * 31 + Kotlin.hashCode(this.fullName) | 0;
    result = result * 31 + Kotlin.hashCode(this.emailAddress) | 0;
    result = result * 31 + Kotlin.hashCode(this.imgUrl) | 0;
    return result;
  };
  User.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.userId, other.userId) && Kotlin.equals(this.userName, other.userName) && Kotlin.equals(this.fullName, other.fullName) && Kotlin.equals(this.emailAddress, other.emailAddress) && Kotlin.equals(this.imgUrl, other.imgUrl)))));
  };
  function RequestPoll(pollQuestion, pollOptions) {
    this.pollQuestion = pollQuestion;
    this.pollOptions = pollOptions;
  }
  RequestPoll.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RequestPoll',
    interfaces: []
  };
  RequestPoll.prototype.component1 = function () {
    return this.pollQuestion;
  };
  RequestPoll.prototype.component2 = function () {
    return this.pollOptions;
  };
  RequestPoll.prototype.copy_c1kmwu$ = function (pollQuestion, pollOptions) {
    return new RequestPoll(pollQuestion === void 0 ? this.pollQuestion : pollQuestion, pollOptions === void 0 ? this.pollOptions : pollOptions);
  };
  RequestPoll.prototype.toString = function () {
    return 'RequestPoll(pollQuestion=' + Kotlin.toString(this.pollQuestion) + (', pollOptions=' + Kotlin.toString(this.pollOptions)) + ')';
  };
  RequestPoll.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.pollQuestion) | 0;
    result = result * 31 + Kotlin.hashCode(this.pollOptions) | 0;
    return result;
  };
  RequestPoll.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.pollQuestion, other.pollQuestion) && Kotlin.equals(this.pollOptions, other.pollOptions)))));
  };
  function ResponsePoll(pollId, pollQuestion, pollOptions) {
    this.pollId = pollId;
    this.pollQuestion = pollQuestion;
    this.pollOptions = pollOptions;
  }
  ResponsePoll.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ResponsePoll',
    interfaces: []
  };
  ResponsePoll.prototype.component1 = function () {
    return this.pollId;
  };
  ResponsePoll.prototype.component2 = function () {
    return this.pollQuestion;
  };
  ResponsePoll.prototype.component3 = function () {
    return this.pollOptions;
  };
  ResponsePoll.prototype.copy_xa3kb8$ = function (pollId, pollQuestion, pollOptions) {
    return new ResponsePoll(pollId === void 0 ? this.pollId : pollId, pollQuestion === void 0 ? this.pollQuestion : pollQuestion, pollOptions === void 0 ? this.pollOptions : pollOptions);
  };
  ResponsePoll.prototype.toString = function () {
    return 'ResponsePoll(pollId=' + Kotlin.toString(this.pollId) + (', pollQuestion=' + Kotlin.toString(this.pollQuestion)) + (', pollOptions=' + Kotlin.toString(this.pollOptions)) + ')';
  };
  ResponsePoll.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.pollId) | 0;
    result = result * 31 + Kotlin.hashCode(this.pollQuestion) | 0;
    result = result * 31 + Kotlin.hashCode(this.pollOptions) | 0;
    return result;
  };
  ResponsePoll.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.pollId, other.pollId) && Kotlin.equals(this.pollQuestion, other.pollQuestion) && Kotlin.equals(this.pollOptions, other.pollOptions)))));
  };
  function Polls(polls) {
    this.polls = polls;
  }
  Polls.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Polls',
    interfaces: []
  };
  Polls.prototype.component1 = function () {
    return this.polls;
  };
  Polls.prototype.copy_7vum37$ = function (polls) {
    return new Polls(polls === void 0 ? this.polls : polls);
  };
  Polls.prototype.toString = function () {
    return 'Polls(polls=' + Kotlin.toString(this.polls) + ')';
  };
  Polls.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.polls) | 0;
    return result;
  };
  Polls.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.polls, other.polls))));
  };
  function getRandomPollId() {
    return Math.floor(Math.random() * 2147483647) + 1000 | 0;
  }
  _.main_kand9s$ = main;
  var package$models = _.models || (_.models = {});
  package$models.User = User;
  package$models.RequestPoll = RequestPoll;
  package$models.ResponsePoll = ResponsePoll;
  package$models.Polls = Polls;
  var package$utils = _.utils || (_.utils = {});
  package$utils.getRandomPollId = getRandomPollId;
  main([]);
  Kotlin.defineModule('index', _);
  return _;
}(module.exports, require('kotlin')));

//# sourceMappingURL=index.js.map
