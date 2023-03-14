function updateFinishDate(){
    let createDateField = document.getElementById('createDate');
    let durationField = document.getElementById('duration');
    let finishDateField = document.getElementById('finishDate');
    let finishDateLabel = document.getElementById('finishDate-label');

    if (createDateField.value.length !== 0 && ( 0 < parseInt(durationField.value) && parseInt(durationField.value) <= 100)){
        let creationDate = new Date(createDateField.value);
        let duration = parseInt(durationField.value);
        creationDate.setFullYear(creationDate.getFullYear() + duration);

        finishDateField.hidden = false;
        finishDateLabel.hidden = false;
        finishDateField.setAttribute('value', creationDate.toISOString().split('T')[0]);
    }else{
        finishDateLabel.hidden = true;
        finishDateField.hidden = true;
        finishDateField.setAttribute('value', '' );
    }
}

function updateAbsoluteFee(){
    let circulationField = document.getElementById('circulation');
    let costPriceField = document.getElementById('costPrice');
    let sellingPriceField = document.getElementById('sellingPrice');
    let absoluteFeeField = document.getElementById('absoluteFee');

    if (sellingPriceField.value !== '0' &&
        costPriceField.value !== '0' &&
        circulationField.value !== '0')
    {
        let absoluteFee = (parseInt(sellingPriceField.value) - parseInt(costPriceField.value)) * parseInt(circulationField.value);
        if (absoluteFee >= 0){
            absoluteFeeField.setAttribute('value',
                ''+ Math.round(absoluteFee/1000)) ;
        }
    }
}