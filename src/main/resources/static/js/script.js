function updateFinishDate(){
    let createDateField = document.getElementById('createDate');
    let durationField = document.getElementById('duration');
    let finishDateField = document.getElementById('finishDate');
    let finishDateLabel = document.getElementById('finishDate-label');

    if (createDateField.value.length !== 0 && ( 0 <= parseInt(durationField.value) && parseInt(durationField.value) <= 100)){
        let creationDate = new Date(createDateField.value);
        let duration = parseInt(durationField.value);
        creationDate.setFullYear(creationDate.getFullYear() + duration);
        finishDateField.setAttribute('value', creationDate.toISOString().split('T')[0]);
    }else{
        finishDateField.setAttribute('value', '' );
    }
}

function updateAbsoluteFee(){
    let circulationField = document.getElementById('circulation');
    let costPriceField = document.getElementById('costPrice');
    let sellingPriceField = document.getElementById('sellingPrice');
    let absoluteFeeField = document.getElementById('absoluteFee');

    if (!fieldIsEmpty(sellingPriceField) &&
        !fieldIsEmpty(costPriceField) &&
        !fieldIsEmpty(circulationField))
    {
        let absoluteFee = (parseInt(sellingPriceField.value) - parseInt(costPriceField.value)) * parseInt(circulationField.value);
        console.log()
        if (absoluteFee >= 0){
            absoluteFeeField.setAttribute('value',
                '' + Math.round(absoluteFee/1000)) ;
        }
    }
}
function fieldIsEmpty(field){
    return field.value.length === 0 || field.value === '0';
}

/* Скрипт для сокрытия формы выбора заказчика
    и отображения формы его создания*/
function showCreateCustomerForm(){
    document.getElementById("choose-customer-form").style.display = "none";
    document.getElementById("create-customer-form").style.display = "flex";
    document.getElementById('defaultSelectCustomer').selected = true;
}
/* Скрипт для сокрытия формы создания заказчика
*  и отображение формы его выбора из предложенных*/
function showChooseCustomerForm(){
    document.getElementById("choose-customer-form").style.display = "flex";
    document.getElementById("create-customer-form").style.display = "none";
    document.getElementById('company').value = '';
    document.getElementById('surname').value = '' ;
    document.getElementById('name').value = '' ;
    document.getElementById('patronymic').value = '' ;
    document.getElementById('address').value = '' ;
    document.getElementById('phoneNumber').value = '' ;
}

/* Скрипт, устанавливает минимальную дату для даты завершения заказа */
function setMinFinishDate(){
    let min = document.getElementById('createDate').value;
    let finishDate = document.getElementById('finishDate');
    if (finishDate.value < min){
        finishDate.value = min;
    }
    finishDate.setAttribute('min', min);
}

function setMinSellingPrice(){
    let min = document.getElementById('costPrice').value;
    let sellingPrice = document.getElementById('sellingPrice');
    if (sellingPrice.value < min){
        sellingPrice.value = min;
    }
    sellingPrice.setAttribute('min', min);
}
function setZeroOnEmpty(id){
    let field = document.getElementById(id);
    if (fieldIsEmpty(field)){
        field.setAttribute('value', '0');
    }
}

/* Устанавливает текущее значение на минимальное разрешенное*/
function setMin(id){
    let field = document.getElementById(id);
    if (parseInt(field.value) < parseInt(field.min)){
        field.value = field.min;
    }
    if (id === 'duration'){
        updateFinishDate();
    }
}
function setMax(id){
    let field = document.getElementById(id);
    console.log(field.value +"-"+ field.max);
    if (parseInt(field.value) > parseInt(field.max)){
        field.value = field.max;
    }
    if (id === 'duration'){
        updateFinishDate();
    }
}

function setCorrectFioField(id){
    let field = document.getElementById(id);
    field.value = field.value.replace(/[^A-Za-zА-Яа-я]*/g,"");

    let fieldValue = field.value;
    if (fieldValue.length !== 0)
        field.value = fieldValue.at(0).toUpperCase() + fieldValue.substring(1,fieldValue.length);

}
$("#phoneNumber").mask("+7 (999) 999-99-99");

