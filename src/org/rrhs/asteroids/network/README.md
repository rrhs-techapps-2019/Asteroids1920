# networking with asteroids

Networking is handled through HashMaps. Each request will include a few data points, specified by the action.

## add

* `action`: `"add"`
* `type`: `actor.getType()` or the string type of the actor
* `actor`: `actor.toString()`

## update

* `action`: `"update"`
* `type`: `actor.getType()`
* `actor`: `actor.toString()`