chessdog
========

chessdog is a chess program that runs directly inside your browser. It can easily be embedded into other websites via a simple JavaScript API.

Getting Started
---------------

Here is a complete example. Insert this into any HTML page and a chess board will appear:

    <div id="board"></div>
    <script type="text/javascript" src="http://chessdog.christophdietze.com/chessdog.js"></script>
    <script type="text/javascript">
      var board = new chessdog.Board(document.getElementById("board"));
    </script>

Let's dissect what this does:

1. Create a element where the board should be inserted, e.g., 

        <div id="board"></div>

2. Include the chessdog script

        <script type="text/javascript" src="http://chessdog.christophdietze.com/chessdog.js"></script>
    

3. Now, we can use the chessdog JavaScript API to create a chess board at the element. An iframe of a fixed size will be created as the child of the specified element.

        <script type="text/javascript">
          var board = new chessdog.Board(document.getElementById("board"));
        </script>

4. Additionally, the API provides the following functions:

        board.reset()
        board.getFen()
        board.setFen(String fenString)
        board.getPgn()
        board.setPgn(String pgnString)
        board.onReady(function func)
        board.allowUserMoves(boolean allowed)

Demos
-----

* [Board](http://chessdog.christophdietze.com/demo/JackEmbedDemo.html)
* [Two boards](http://chessdog.christophdietze.com/demo/JackEmbed2Frames.html)
* [Board showing the Immortal Game read from PGN](http://chessdog.christophdietze.com/demo/ImmortalGameDemo.html)

Building from source
--------------------
In order to build from source you will need [sbt](https://github.com/harrah/xsbt) v0.11.

    git clone git@github.com:thunderklaus/chessdog.git
    cd chessdog
    sbt package-war

After the build finished successfully, the final war file will be in the target directory:

    ./target/scala_xxx/
