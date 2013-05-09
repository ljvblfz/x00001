

Array.prototype.removeByElement = function (element) {
	for(var i=0; i<this.length;i++ ){ 
		  if(this[i]==element){
			  this.splice(i,1);
		  }
	} 
}



Array.prototype.findObjectByName = function (nameOfObj) {
	for(var i=0; i<this.length;i++ ){ 
		  if(this[i].name==nameOfObj){
			  return this[i];
		  }
	} 
	return null;
}


/*
Array.prototype.removeByElement(element){
	for(var i=0; i<this.length;i++ ){ 
		  if(this[i]==element)
			  this.splice(i,1); 
	} 
};

Array.prototype.findObjectByName(nameOfObj){
	for(var i=0; i<this.length;i++ ){ 
		  if(this[i].name==nameOfObj){
			  return this[i];
		  }
	} 
	return null;
};*/