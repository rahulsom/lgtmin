import com.google.appengine.api.datastore.*

def k = new KeyFactory.Builder("Person", "GreatGrandpa").getKey()
log.info k.toString()
log.info KeyFactory.keyToString(k)

def e = new Entity("person")
e.firstname = 'Marco'
e.lastname = 'Vermeulen'
e.save()
log.info e
log.info e.key

e = new Entity("person")
e.firstname = 'Marco'
e.lastname = 'Rubio'
e.save()
log.info e
log.info e.key