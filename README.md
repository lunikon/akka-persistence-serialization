# Akka Persistence and Protocol Buffers

This project is the bastard child of the Activator Akka Persistence Scala template and the Protocol Buffer code from the Akka Persistence module itself. It's a rough sketch of how event and state versioning using protocol buffers can be achieved.

My most important insight so far: This becomes really messy really fast. You need serialization and deserialization logic for every single event object which in a large project can become a lot. I tried to address this by doing a double abstraction: The serializer configured in Akka looks up case class-specific adapters from a static registry and delegates the serialization/deserialization to this adapter. This allows for two things:

1. The serialization/deserialization logic can be kept in the same file as the corresponding event/state class (if desired).
2. The registry is easily extendable.

The code's a mess and badly documented right now. This is just a little experiment.
