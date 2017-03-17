/**
 * Created by melikaayoughi on 3/17/17.
 */
// window.onload = function()

// window.onload = countdown(10);

// function countdown(minutes) {
//     var seconds = 60;
//     var mins = minutes;
//     function tick() {
//         var counter = document.getElementById("time");
//         var current_minutes = mins-1;
//         seconds--;
//         counter.innerHTML = current_minutes.toString() + ":" + (seconds < 10 ? "0" : "") + String(seconds);
//         if( seconds > 0 ) {
//             setTimeout(tick, 1000);
//         }
//         else {
//             if(mins > 1){
//                 countdown(mins-1);
//             }
//         }
//     }
//     tick();
// }
//
// countdown(10);


class ReserveFieldsManager {

    constructor(name,surname,id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        // alert("name: "+name+" surname: "+surname+" id: "+id);
    }

    checkStructuralValidity() {
        //name validity
        if (this.name.length < 3 || !(this.name.match(/[a-zA-Z]*/))){
            // alert("this.name.length: "+this.name.length+" match: "+this.name.match(/[a-zA-Z]*/) )
            alert("first if");
            return false;
        }
        //surname validity
        else if (this.surname.length < 3 || !(this.surname.match(/[a-zA-Z]*/))){
            alert("second if");
            return false;
        }
        //id validity
        else if (this.id.length != 10 || !(this.id.match(/[0-9]*/))){
            alert("third if");
            return false;
        }
        // if (!this.message.match(/\d+/)) return false;
        else {
            alert("else");
            return true;
        }

    }
}


function checkAndSubmit(totalRows) {
    // alert(totalRows);
    for (let i=1; i<=totalRows; i++){
        let reserveFieldManager = new ReserveFieldsManager(document.getElementsByName("name-"+i)[0].value,
            document.getElementsByName("surname-"+i)[0].value, document.getElementsByName("id-"+i)[0].value);
        let result = reserveFieldManager.checkStructuralValidity();
        alert("i: "+i+"result: "+result);
        if(!result){
            document.getElementById("wrongPassengerInfoAlert").hidden = false;
            break;
        }
        else{
            alert("submit form");
            document.getElementById("passenger-form").submit();
        }
    }
}