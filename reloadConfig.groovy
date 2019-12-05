def reloadItems() {
  Jenkins.instance.getAllItems(AbstractItem.class).each { 
    it.doReload() 
  }
}
