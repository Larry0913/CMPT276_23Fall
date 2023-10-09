// var n = window.prompt("name?", "Larry")
// window.alert("Hello" + n)
// // console.log("hello" + n)

// for (var i = 0; i < 10; i++)
// {
//     console.log(i)
// }

var person = {
    name: 'Larry',
    age: 22,
    address:{
        number: '123',
        street: '1233'
    } 

}

function myfunc() {
    console.log('Hello')
    console.log('8888')
}

var i = 0
// window.setInterval(
//     function(){
//         window.alert("Hello")
//         i++
//     },
//     1000)

window.addEventListener('keypress', processKey)

function processKey(evt) {
    console.log(evt)
}