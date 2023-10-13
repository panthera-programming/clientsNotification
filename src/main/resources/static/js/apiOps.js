
//const baseUrl = "http://localhost:8080"
const baseUrl = "https://meladenservice.azurewebsites.net"

function formToJson(formContainer, urlLink, respIndicator, failIndicator)
{
    document.getElementById(formContainer).addEventListener("submit", (form) => {
        form.preventDefault()
        const formData = new FormData(form.target)
        const jsonData = {}
        formData.forEach((value, key) => {
            jsonData[key] = value
        })
        postData(urlLink, jsonData, respIndicator, failIndicator)  
    })
}

function saveProdClient(formContainer, urlLink, respIndicator, failIndicator, num)
{
    document.getElementById(formContainer).addEventListener("submit", (form) => {
        form.preventDefault()
        const formData = new FormData(form.target)
        const jsonData = {}
        formData.forEach((value, key) => {
            jsonData[key] = value
        })
        jsonData["product"] = allProdArray[num]
        console.log(jsonData)
        postData(urlLink, jsonData, respIndicator, failIndicator)  
    })
}


async function perProdClients(num)
{
    var url = `${baseUrl}/api/client/all?prodId=${num}`

    const endpointRes = await fetch(url, 
        {
            method : "GET"
        }
    )
    const promiseRes =  await endpointRes.json()
    var prodClientsArray = []
    prodClientsArray = promiseRes.data.clients

    return (prodClientsArray)
}

function postData(url, data, respIndicator, failIndicator)
{
    fetch(url, 
        {
            method : "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(data)
        }
    ).then((response) => {
        return (response.json())
    }).then(data => {
        if (data.statusCode === 201)
        {
            document.getElementById(respIndicator).classList.replace("d-none", "d-flex")
        }
        else{
            document.getElementById(failIndicator).classList.replace("d-none", "d-flex")
        }
        
    })
}

//let allClientArray = []
let allProdArray = []

async function loadAllProducts(){

    const url = `${baseUrl}/api/product/all`
    const endpointRes = await fetch(url, 
        {
            method : "GET"
        }
    )
    const promiseRes =  await endpointRes.json()
    const productArray = promiseRes.data.products
    allProdArray = productArray
    var count = 0
    for (let product of productArray)
    {
        //adding products' names to drop down
        liTag = document.createElement("li")
        aTag = document.createElement("a")
        aTag.innerText = product.name
        aTag.setAttribute("id", `prodNavLink${count + 1}`)
        aTag.setAttribute("href", "#selected-prod-clients")
        liTag.appendChild(aTag)
        document.querySelector(".drop-down-client").appendChild(liTag)

        //Adding products to table
        const tableRaw = document.createElement("tr")
        const field1 = document.createElement("td")
        field1.innerText = product.id
        tableRaw.appendChild(field1)
        const field2 = document.createElement("td")
        const field2A = document.createElement("a")
        field2A.setAttribute("href", "#selected-prod-clients")
        field2A.setAttribute("class", "prod-name-link")
        field2A.setAttribute("id", `prod${count + 1}`)
        field2A.innerText = product.name
        
        field2.appendChild(field2A)
        tableRaw.appendChild(field2)
        const field3 = document.createElement("td")
        field3.innerText = product.value
        tableRaw.appendChild(field3)

        document.querySelector(".prod-table").appendChild(tableRaw)

        count++
    }
    processProdClick()
}

function processProdClick()
{
    var allProdNamesArray = []
    var allProdNamesText = []
    var count = 0

    var allProdNamesArray1 = []
    var allProdNamesText1 = []

    while (count < allProdArray.length)
    {
        allProdNamesArray.push(document.getElementById(`prod${count + 1}`))
        allProdNamesText.push(document.getElementById(`prod${count + 1}`).textContent)

        allProdNamesArray1.push(document.getElementById(`prodNavLink${count + 1}`))
        count++
    }
    count = 0
    allProdNamesArray.forEach((prodName) => {
        var pos = allProdNamesArray.indexOf(prodName)
        prodName.addEventListener("click", () => {
            if (document.querySelector("h4#product-name").innerText == allProdNamesText[pos])
            {
                console.log("Property already selected..!")
            }
            else
            { 
                prodClicked(pos)
                document.querySelector("h4#product-name").innerText = allProdNamesText[pos]
                saveProdClient("create-client-form", `${baseUrl}/api/client/new`, "client-response-success", "client-response-failure",pos)
            }
        })
    })
    count = 0
    allProdNamesArray1.forEach((prodName) => {
        var pos = allProdNamesArray1.indexOf(prodName)
        prodName.addEventListener("click", () => {
            if (document.querySelector("h4#product-name").innerText == prodName.innerText)
            {
                console.log("Property already selected..!")
            }
            else
            { 
                prodClicked(pos)
                document.querySelector("h4#product-name").innerText = prodName.innerText
                saveProdClient("create-client-form", `${baseUrl}/api/client/new`, "client-response-success", "client-response-failure",pos)
            }
        })
    })
}

async function prodClicked(num)
{
    var clients = []

    var prodId = allProdArray[num].id

    clients = await perProdClients(prodId)
    console.log(clients)
    document.querySelector(".clients-table-body").innerHTML = ""
    if (clients.length == 0)
    {
        tableRaw = document.createElement("tr")
        field = document.createElement("td")
        field.innerText = "No clients yet. Add clients to this property..."
        field.setAttribute("colspan", "6")
        tableRaw.appendChild(field)
        document.querySelector(".clients-table-body").appendChild(tableRaw)
    }
    else
    {  
        var i = 0  
        for (var client of clients)
        {
            tableRaw = document.createElement("tr")
            field1 = document.createElement("td")
            field1.innerText = client.id
            tableRaw.appendChild(field1)
            field2 = document.createElement("td")
            field2.innerText = client.name
            tableRaw.appendChild(field2)

            field3 = document.createElement("td")
            field3A = document.createElement("a")
            field3A.innerText = client.email
            field3A.setAttribute("class", "client-email")
            field3A.setAttribute("href", "#bulk-mail-form")
            field3A.addEventListener("click", () => {
                perClientOps("mail","bulk-mail-form","cancel-mail","mail-response-success","mail-response-failure",".mail-reciever",field1.innerText)
            })
            field3.appendChild(field3A)
            tableRaw.appendChild(field3)

            field4 = document.createElement("td")
            field4A = document.createElement("a")
            field4A.innerText = client.phone
            field4A.setAttribute("class", "client-phone")
            field4A.setAttribute("href", "#bulk-sms-form")
            field4A.addEventListener("click", () => {
                perClientOps("sms","bulk-sms-form","cancel-sms","sms-response-success","sms-response-failure",".sms-reciever",field1.innerText)
            })
            field4.appendChild(field4A)
            tableRaw.appendChild(field4)

            /*field5 = document.createElement("td")
            spanField5 = document.createElement("span")
            spanField5.setAttribute("class", "icon-container")
            spanField5A = document.createElement("a")
            spanField5A.setAttribute("class", "material-symbols-rounded")
            spanField5A.innerText = "edit"
            spanField5.appendChild(spanField5A)
            field5.appendChild(spanField5)
            tableRaw.appendChild(field5)*/

            field6 = document.createElement("td")
            spanField6 = document.createElement("span")
            spanField6.classList.add("icon-container", "delete-client")
            //spanField6.setAttribute("class", "icon-container")
            /**/
            spanField6A = document.createElement("a")
            spanField6A.setAttribute("class", "material-symbols-rounded")
            spanField6A.classList.add("delete-icon")
            spanField6A.innerText = "delete"
            spanField6.appendChild(spanField6A)
            field6.appendChild(spanField6)
            tableRaw.appendChild(field6)

            document.querySelector(".clients-table-body").appendChild(tableRaw)
        }
    }
    deleteClient(clients)
}
function reloadClients()
{
    document.getElementById("reload-prod-clients").addEventListener("click", () => {
        console.log("Hit the clients' reload button!")
        var prodName = document.querySelector("h4#product-name").innerText    
        
        for (let prod of allProdArray)
        {
            if (prodName == prod.name)
            {
                prodClicked(allProdArray.indexOf(prod))
                break
            }
        }
        document.querySelectorAll("input").forEach((elem) => {
            elem.innerText = ""
        })
    })
}

/**/

/*****************************************/

function handleDropDowns()
{
    document.getElementById("toggleDrop1").addEventListener("click", () => {
        document.querySelector(".drop-down-prod").classList.toggle("d-none")
    })
    document.getElementById("toggleDrop2").addEventListener("click", () => {
        document.querySelector(".drop-down-client").classList.toggle("d-none")
        document.querySelector(".drop-down-client").classList.toggle("d-flex")
    })
}
function generalOperations(linkBtn, container, cancelBtn, respIndicator, failIndicator,msgType)
{
    document.getElementById(linkBtn).addEventListener("click", () => {
        document.getElementById(container).classList.replace("d-none", "d-block")
        document.querySelector(".mail-reciever").innerText = "All Clients"
        document.querySelector(".sms-reciever").innerText = "All Clients"
        sendMailSms(msgType,container)
    })
    document.getElementById(cancelBtn).addEventListener("click", () => {
        document.getElementById(container).classList.replace("d-block", "d-none")
        document.getElementById(respIndicator).classList.replace("d-flex", "d-none")
        document.getElementById(failIndicator).classList.replace("d-flex", "d-none")
    })
}
function sendMailSms(msgType,container)
{
    const propertyName = document.querySelector("h4#product-name").innerText
    var prodId
    console.log(allProdArray)
    for (let prod of allProdArray)
    {
        if (prod.name == propertyName)
        {
            prodId = prod.id
            console.log("Selected product's id = " + prod.id)
            
        }
    }
    
    if (msgType === "sms")
    {
        const link = `${baseUrl}/api/client/sms/all?prodId=${prodId}`
        sendSms(container,link)
    }
    else
    {
        const link = `${baseUrl}/api/client/mail/all?prodId=${prodId}`
        sendMail(container,link)
    }
}

function perClientOps(msgType,container,cancelBtn,respIndicator,failIndicator,rcvr,id)
{
    console.log("client of id " + id + " clicked")
    //document.getElementById(container).classList.replace("d-none", "d-block")
    document.querySelector(rcvr).innerText = "Client"
    if (msgType === "sms")
    {
        sendSms(container)
    }
    else
    {
        sendMail(container)
    }
    document.getElementById(cancelBtn).addEventListener("click", () => {
        document.getElementById(container).classList.replace("d-block", "d-none")
        document.getElementById(respIndicator).classList.replace("d-flex", "d-none")
        document.getElementById(failIndicator).classList.replace("d-flex", "d-none")
    })
}

function deleteClient(prodClientArray)
{
    var clients = []
    clients = document.querySelectorAll(".delete-client")
    clients.forEach((client) => {
        client.addEventListener("click", async () => {
            var clientId = prodClientArray[clients.indexOf(client)].id
            const url = `${baseUrl}/api/client/delete?clientId=${clientId}`
            console.log("Deleting client of id " + clientId)
            console.log("Delete request to be sent to " + url)
            const resp = await fetch(url, 
                {
                    method : "DELETE"
                }
            )
            console.log(resp)
        })
    })
}
function sendMail(formContainer,endpointLink){
    document.getElementById(formContainer).addEventListener("submit", (form) => {
        form.preventDefault()
        const msgJson = {}
        msgJson["message"] = document.getElementById("mail-textarea").value
        postData(endpointLink,msgJson,"mail-response-success","mail-response-failure")
        console.log(msgJson) 
    })
}
function sendSms(formContainer,endpointLink){
    document.getElementById(formContainer).addEventListener("submit", (form) => {
        form.preventDefault()
        const msgJson = {}
        msgJson["message"] = document.getElementById("sms-textarea").value
        postData(endpointLink,msgJson,"sms-response-success","sms-response-failure")
        console.log(msgJson) 
    })
}

/*******************************************************/

window.onload = loadAllProducts();

document.addEventListener("DOMContentLoaded", () => {
    formToJson("create-product-form", `${baseUrl}/api/product/new`, "prod-response-success", "prod-response-failure")
    //formToJson("create-client-form", `${baseUrl}/api/client/new`, "client-response-success", "client-response-failure")
    formToJson("create-staff-form", `${baseUrl}/api/staff/new`, "staff-response-success", "staff-response-failure")
    reloadClients()

    handleDropDowns()
    generalOperations("bulk-sms-link","bulk-sms-form","cancel-sms","sms-response-success","sms-response-failure","sms")
    generalOperations("bulk-mail-link","bulk-mail-form","cancel-mail","mail-response-success","mail-response-failure","mail")
    generalOperations("create-product-link","create-product-form","cancel-product","prod-response-success","prod-response-failure")
    generalOperations("create-client-link","create-client-form","cancel-client","client-response-success","client-response-failure")
   
})


