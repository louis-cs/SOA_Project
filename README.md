# SOA_Project
https://github.com/louis-cs/SOA_Project

## http://localhost:8888
Activation&Deactivation: _...8888_/loop 
---
* Reset OM2M values: _...8888_/reset
* Reset all rooms: _...8888_/room/reset	(**warning**: _deactivate_ then _reset_)
---
- Switch off *alarm*: _...8888_/alarm
- Change *time*: _...8888_/time/<hour>/<min>
- Change *presence*: _...8888_/movement/<boolean>
- Change *luminosity*: _...8888_/light/<int>
---
Search OM2M tree: _...8888_/r/ty=3

### Services
Get: <service>?room=<int>&id=<int>
Set: <service>/set?room=<int>&id=<int>&val=<int or boolean>

#### Temperature Service
http://localhost:8081
- temperature inside: /temperature/inside
- temperature outside: /temperature/outside
- window: /window
- radiator: /radiator

#### Light Service
http://localhost:8082
- luminosity: /light
- led: /led

#### Movement Service
http://localhost:8083
- presence: /movement
- alarm: /alarm
- door: /door
--
### Create & Delete
- .../create?...
- .../delete?...