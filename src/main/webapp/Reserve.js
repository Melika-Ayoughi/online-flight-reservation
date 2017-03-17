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
        if (this.name.length < 3 || !(this.name.match(/^[a-zA-Z]*$/))){
            // document.getElementsByName(this.name)[0].focus();
            // alert("this.name.length: "+this.name.length+" match: "+this.name.match(/[a-zA-Z]*/) )
            // alert("first if");
            return false;
        }
        //surname validity
        else if (this.surname.length < 3 || !(this.surname.match(/^[a-zA-Z]*$/))){
            // document.getElementsByName(this.surname)[0].focus();
            // alert("second if");
            return false;
        }
        //id validity
        else if (this.id.length != 10 || !(this.id.match(/^[0-9]*$/))){
            // document.getElementsByName(this.id)[0].focus();
            // alert("third if");
            return false;
        }
        // if (!this.message.match(/\d+/)) return false;
        else {
            // alert("else");
            return true;
        }

    }
}


function validateForm(totalRows) {
    // alert(totalRows);

    let wrongCount = 0;
    for (let i=1; i<=totalRows; i++){
        let reserveFieldManager = new ReserveFieldsManager(document.getElementsByName("name-"+i)[0].value,
            document.getElementsByName("surname-"+i)[0].value, document.getElementsByName("id-"+i)[0].value);
        let result = reserveFieldManager.checkStructuralValidity();
        // alert("i: "+i+"result: "+result);
        if(!result){
            // alert("wrong field");
            wrongCount++;
        }
        else{
            // alert("right field");
        }
    }



    if (wrongCount == 0){
        // alert("return true");
        // return true;
        document.getElementById("passenger-form").submit();
    }
    else {
        alert("return false");
        // return false;
        document.getElementById("wrongPassengerInfoAlert").hidden = false;
        alert("اطلاعات مسافران غلط وارد شده است.");
    }

}

function checkCapacity(passengerCount) {
    var capacity = parseInt(document.getElementsByClassName("hidden-xs hidden-sm hidden-md hidden-lg")[0].innerHTML);
    return (capacity >= passengerCount)
}

function updatePayment(adultCount, childCount, infantCount) {
    var firstLine = document.getElementById("first-line");
    var adultPrice = parseInt(firstLine.getElementsByTagName("span")[0].innerHTML);
    firstLine.getElementsByTagName("span")[2].innerHTML = adultCount;
    firstLine.getElementsByTagName("span")[4].innerHTML = adultCount*adultPrice;

    var secondLine = document.getElementById("second-line");
    var childPrice = parseInt(secondLine.getElementsByTagName("span")[0].innerHTML);
    secondLine.getElementsByTagName("span")[2].innerHTML = childCount;
    secondLine.getElementsByTagName("span")[4].innerHTML = childCount*childPrice;

    var thirdLine = document.getElementById("third-line");
    var infantPrice = parseInt(thirdLine.getElementsByTagName("span")[0].innerHTML);
    thirdLine.getElementsByTagName("span")[2].innerHTML = infantCount;
    thirdLine.getElementsByTagName("span")[4].innerHTML = infantCount*infantPrice;

    var lastLine = document.getElementById("last-line");
    lastLine.getElementsByTagName("span")[0].innerHTML = adultCount*adultPrice + childCount*childPrice + infantCount*infantPrice;
}

function updateInformation(adultCount, childCount, infantCount) {

}

function update() {
    var adultCount = parseInt(document.getElementById("adultCount").value);
    var childCount = parseInt(document.getElementById("childCount").value);
    var infantCount = parseInt(document.getElementById("infantCount").value);

    if(!checkCapacity(adultCount+childCount+infantCount))
        return;

    updatePayment(adultCount, childCount, infantCount);
    updateInformation(adultCount, childCount, infantCount);
}