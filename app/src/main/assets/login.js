

		function delInfoToJs(id) {
              var et = document.getElementById(id);
              et.value = et.value.substring(0, et.value.length - 1);
		}
		function addInfoToJs(id,info) {
              var et = document.getElementById(id);
              et.value += info;
		}
//    if (isAndroid) {
//        $("#phoneNumberInput").click(function() {
//            client.callJava("phoneNumberInput");
//        });
//
//        $("#passwordInput").click(function() {
//            client.callJava("passwordInput");
//        });
//    }