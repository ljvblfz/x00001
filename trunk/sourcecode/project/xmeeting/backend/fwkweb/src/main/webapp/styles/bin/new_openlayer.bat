cd D:\workspace\syWeb\src\main\webapp\styles\bin

REM -------------- start package javascript --------------

type ..\openlayer_src\jshashtable_src.js > openlayerESC.js
type ..\openlayer_src\jshashtable.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.bus.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.core.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.custom.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.extend.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.init.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.landmark.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.layer.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.linepath.js >> openlayerESC.js

type ..\openlayer_src\openLayer_mapBus.station.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.style.js >> openlayerESC.js
type ..\openlayer_src\openLayer_mapBus.util.js >> openlayerESC.js
type ..\openlayer_src\OpenLayers.js >> openlayerESC.js

cscript ESC.wsf -l 1 -ow openlayerESC1.js openlayerESC.js
cscript ESC.wsf -l 2 -ow openlayerESC2.js openlayerESC1.js
cscript ESC.wsf -l 3 -ow openlayerESC3.js openlayerESC2.js

type openlayerESC2.js > openlayer.min.js
#gzip -f openlayer.min.js
#copy openlayer.min.js.gz openlayer.min.gzjs /y

del openlayerESC*.js
del openlayer.min.js.gz