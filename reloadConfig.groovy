
 evaluate(Jenkins.instance.getAllItems(AbstractItem.class).each { 
  it.doReload() 
};)
