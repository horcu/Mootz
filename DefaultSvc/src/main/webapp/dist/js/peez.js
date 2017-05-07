


 // A function that attaches a "Send Message" button click handler
    function enableClick() {
      document.getElementById('sendMessageButton').onclick = function() {
        var message = document.getElementById('messageTextInput').value;
        if (!message) {
          message = '(Empty message)';
        }

        gapi.client.messaging.messagingEndpoint.sendMessage({'message': message}).execute(
          function(response) {
            var outputAlertDiv = document.getElementById('outputAlert');
            outputAlertDiv.style.visibility = 'visible';

            if (response && response.error) {
              outputAlertDiv.className = 'alert alert-danger';
              outputAlertDiv.innerHTML = '<b>Error Code:</b> ' + response.error.code + ' [' + response.error.message +']';
            }
            else {
              outputAlertDiv.className = 'alert alert-success';
              outputAlertDiv.innerHTML = '<b>Success:</b> Message \"' + message + '\" sent to all registered devices!</h2>';
            }
          }
        );
        return false;
      }
    }

    // This is called initially
    function init() {
      var apiName = 'messaging'
      var apiVersion = 'v1'
      var apiRoot = 'https://' + window.location.host + '/_ah/api';
      if (window.location.hostname == 'localhost'
          || window.location.hostname == '127.0.0.1'
          || ((window.location.port != "") && (window.location.port > 1023))) {
            // We're probably running against the DevAppServer
            apiRoot = 'http://' + window.location.host + '/_ah/api';
      }
      var callback = function() {
        enableClick();
      }
      gapi.client.load(apiName, apiVersion, callback, apiRoot);
    }

