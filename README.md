# Tirada de Dados (Android)

Aplicación Android desarrollada en Kotlin que simula la tirada de tres dados.  
El usuario introduce un número entre 3 y 18 e intenta adivinar la suma resultante.  
Durante la animación, el botón de lanzamiento se bloquea hasta que finaliza la tirada.

## Funcionamiento

1. El usuario introduce un número y pulsa el botón del cubilete.  
2. Se lanzan tres dados simulados con imágenes que cambian varias veces.  
3. Al finalizar la tirada, se muestra la suma total.  
4. Si el número introducido coincide con la suma, se abre una pantalla indicando que ha acertado.

## Detalles técnicos

- Lenguaje: Kotlin  
- Librerías usadas: ViewBinding, Handler, Executors  
- Se utiliza un `Handler` con el `Looper` del hilo principal para actualizar la UI desde un hilo secundario.
- Los dados se actualizan en intervalos cortos usando `schedule` para simular una animación de lanzamiento.

## Archivos principales

- `MainActivity.kt`: Lógica principal del juego.  
- `Acertaste.kt`: Pantalla que se muestra cuando el usuario acierta.  
- `activity_main.xml`: Diseño principal con el botón, los dados y el campo de texto.  
- `activity_acertaste.xml`: Diseño de la pantalla de acierto.

## Instalación y ejecución

1. Clonar o descargar el proyecto.  
2. Abrir con **Android Studio**.  
3. Conectar un emulador o dispositivo Android.  
4. Ejecutar con el botón **Run**.

