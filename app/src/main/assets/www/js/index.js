/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent('deviceready');
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
        var permissions = cordova.plugins.permissions;

        permissions.requestPermission(permissions.CAMERA, success, error);
        permissions.requestPermission(permissions.RECORD_AUDIO, success, error);

        function error() {
          console.warn('Camera permission is not turned on');
        }

        function success( status ) {
          if( !status.hasPermission ) error();
        }
        function getURLParameter(name) {
		    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
	    }
        console.log("Wakwaw");
        var class_id = getURLParameter("class_id");
        var template = getURLParameter("template");
        var id_user = getURLParameter("id_user");
        var p = getURLParameter("p");
        var type = getURLParameter("type");
//        $("#klikakumas").attr('href','https://classmiles.com/master/tps_me/'+class_id+'?t=all_featured&p=android');
        console.warn("ini aku mas "+class_id);
        console.warn("ini aku mas "+template);
        console.warn("ini aku mas "+id_user);
        console.warn("ini aku mas "+p);
        console.warn("ini aku mas "+type);
        var a = '';
        if(type == 'priv') {
            a = 'https://indiclass.id/master/tps_me_priv/' + class_id + '?t=' + template + '&id_user=' + id_user + '&p=' + p;
        } else if(type == 'grup'){
            a = 'https://indiclass.id/master/tps_me_group/' + class_id + '?t=' + template + '&id_user=' + id_user + '&p=' + p;
        } else {
            a = 'https://indiclass.id/master/tps_me/' + class_id + '?t=' + template + '&id_user=' + id_user + '&p=' + p;
        }
        window.location=a;
    }
};

app.initialize();