import _ from 'lodash'

export function paginate(items, pageNumber, pageSize){
    const startIndex = (pageNumber-1)*pageSize;
    // console.log("Start Index: " + startIndex);
    return _(items).slice(startIndex).take(pageSize).value();
    
}

export function previousAndNextBtn(totalItems, pageSize,currentPage, btn){
    const pagesCount = Math.ceil(totalItems/pageSize);
    if(btn=='pre'){
        if(currentPage==1) return currentPage;
        return currentPage-1;
    }
    if(btn=='next'){
        if(currentPage==pagesCount) return currentPage;
        return currentPage+1;
    }
        
}