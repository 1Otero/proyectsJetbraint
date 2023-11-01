var divProducts= document.getElementById("allProductsTop")
var divProductsLess= document.getElementById("allProductsLess")
var cantidadHtml= document.getElementById("cProducts")
var productsTop= []
var productsLess= []
var allProducts= []
var carrito= []
var carritoTest= []

initComp()

function initComp(){
    var carritoL= localStorage.getItem("carrito")
    var count= document.getElementById("")
    if(carritoL != null){
        if(confirm("Tiene productos en el carrito, desea eliminarlos?")){
            localStorage.removeItem("carrito")
            console.log("Se eliminaron los productos del carrito correctamente")
        }else{
            carrito= JSON.parse(carritoL)
        }
    }
    countProducts()
}

function leerTest(){
    carrito.forEach(e => {
        carritoTest.push({product: e})
    })
}

fetch("http://localhost:8080/ini/allProducts", {
    method: "POST",
    mode: "cors",
    headers: {"Content-Type": "application/json"}
})
    .then(e => {
        return e.json()
    })
    .then(e => {
        var htmlFill= ''
        productsTop= []
        productsLess= []
        e.map(e => {
            var jsonBody= JSON.parse(e)
            console.log(jsonBody.name)
            var arrayImage= new Uint8Array(jsonBody.photo)
            console.log(jsonBody.photo)
            var blob= new Blob([arrayImage], {type: 'application/pdf'})
            console.log(blob)
            var url= URL.createObjectURL(blob)
            htmlFill+= `<div style="display:inline-block " class="etiquetaProduct" onclick="infoProduct(${jsonBody.id})">
                         <h1>${jsonBody.name}</h1>
                         <img width="200px" src='${url}' alt="${jsonBody.name}"/> 
                        </div>`
            jsonBody.venta= "less"
            productsLess.push(jsonBody)
            jsonBody.venta= "top"
            productsTop.push(jsonBody)
            allProducts.push(jsonBody)
            divProducts.innerHTML= htmlFill
            divProductsLess.innerHTML= htmlFill
        })
    })
    .catch(e => {
        console.log(e)
        console.log("Error fetch")
    })

var info= document.getElementById("wrapperProductinfo")
var productInfo= document.getElementById("Productinfo")
var divCarrito= document.getElementById("carritoInfo")



function infoProduct(id, tipo){
    var htmlFill= ''
    var product= allProducts.find(e => e.id == id)
    console.log(product.photo)
    var arrayImg= new Uint8Array(product.photo)
    console.log(arrayImg)
    var blob= new Blob([arrayImg], {type: 'application/pdf'})
    var url= URL.createObjectURL(blob)
    htmlFill= `<div>
         <h1 id="titleProduct" onclick="comprar(${product.id})">BUY Product</h1>
         <div style="width="400px"">
            <img src="${url}" width="400px" alt="${product.name}" />
         </div>
         <h2>${product.name}</h2>
         <p>is product for test</p>
    </div>`
    console.log("**************")
    console.log(product)
    console.log("**************")
    info.style.display= "block"
    console.log("Info Id: ")
    console.log(id)
    productInfo.innerHTML= htmlFill
}

function comprar(id){
    var product= allProducts.find(e => e.id == id)
    var localCarrito= localStorage.getItem("carrito")

    if(localCarrito != null){
        localCarrito= JSON.parse(localStorage.getItem("carrito"))
        carrito= localCarrito

        if(localCarrito.find(e => e.id==id) != null && carrito.find(e => e.id==id) != null){
            localCarrito.forEach(e => {
                if(e.id == id){
                    e.cantidad+= 1
                }
            })
        }else{
            localCarrito.push(product)
        }
        carrito= localCarrito
        localStorage.setItem("carrito", JSON.stringify(localCarrito))
        countProducts()
    }else{
       carrito.push(product)
       localStorage.setItem("carrito", JSON.stringify(carrito))
       cantidadHtml.innerHTML= `<h2>${1}</h2>`
    }

}

window.onclick= (e)=> {
    if(e.target == info){
        info.style.display= "none"
    }
    if(e.target == divCarrito){
        divCarrito.style.display= "none"
    }
}

function carritoBuy(){
    var carritoProd= document.getElementById("carritoProducts")
    divCarrito.style.display= "block"
    var html = '<div><h2>Carrito</h2>'
    carrito.forEach(e => {
        var arrayImage= new Uint8Array(e.photo)
        var blob= new Blob([arrayImage], {type: 'application/pdf'})
        var url= URL.createObjectURL(blob)
        html+= `<div class="productCarrito">
                   <img src="${url}" alt="${e.name}"/>
                   <h3>${e.precio}</h3>
                   <h2>${e.name}</h2>
                </div>`
    })
    if(carrito.length == 0){
      html+= '<h2>Not products found</h2>'
    }else{
        html+= `<div>
         <div onclick="enviarProducts()">Pagar</div>
         <div onclick="deleteCarrito()">Limpiar carrito</div>  
        </div>`
    }

    html+= '</div>'
    carritoProd.innerHTML= html
    console.log(carrito.length)
    console.log("see for buy!")
}

//function pagar
function enviarProducts(){
    leerTest()
    console.log("buying")
    //var bodySend= {name: "Ronaldo", mail: "truj@gmail.com", pedidos: [{count: 1, total: 2, products: [{"name": "Arandanos",cantidad: 10, precio: 200.0}]}]}
    //var bodySend= {name: "ivan", mail: "trujilloivanzx@gmail.com", subject: "test uno", body: "this is simple text for test",pedidos: [{count: 1, total: 2, products: carrito,productsPedido: carritoTest}, {count: 1, total: 1, products: carrito,productsPedido: carritoTest}]}
    var bodySend= {name: "ivan", mail: "trujilloivanzx@gmail.com", subject: "test uno", body: "this is simple text for test",pedidos: [{count: 1, total: 2, products: carrito}, {count: 1, total: 1, products: carrito}]}
    fetch("http://localhost:8080/ini/receiveProducts", {
        method: 'POST',
        //headers: {'Content-Type':'application/x-www-form-urlencoded'},
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify(bodySend)
    })
        .then(e => e.text())
        .then(e => {
            console.log(e)
        })
    deleteCarrito()
}

function deleteCarrito(){
    carrito= []
    localStorage.removeItem("carrito")
    carritoBuy()
    countProducts()
}

window.addEventListener("beforeunload", (e) => {
    console.log("Se esta cerrando")
    e.returnValue= "Seguro quieres salir? se borrara su lista de compras!"
})

function countProducts(){
    var cantidad= 0
    if(carrito != null){
        carrito.forEach(e => {
             cantidad+= e.cantidad
        })
    }
    cantidadHtml.innerHTML= `<h2>${cantidad}</h2>`
    cantidadHtml.style.display= 'block'
}
