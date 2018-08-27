<template>
  <div id="app">
    <!--<div id="nav">-->
      <!--<router-link to="/">Home</router-link> |-->
      <!--<router-link to="/about">About</router-link>-->
    <!--</div>-->
    <div class="wrapper">
      <div class="main">
        <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute">
          <div class="container-fluid">
            <div class="navbar-wrapper">
              <span class="navbar-brand">BudgetMonitor</span>
            </div>
          </div>
        </nav>
        <router-view/>
      </div>
    </div>
  </div>
</template>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

body{
  margin:1em auto;
  padding:0 .62em;
}

h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

h1,h2,h3 {
  line-height:1.2;
}

.wrapper {
  position: relative;
  top: 0;
  height: 100vh;
}

.main {
  width: calc(100% - 80px);
  height: 100%;
  max-height: 100%;
}

.main > .navbar {
  margin-bottom: 0;
}

.navbar.navbar-transparent {
  background-color: transparent !important;
  box-shadow: none;
  color: #666;
}
/*.navbar.navbar-absolute {*/
  /*position: absolute;*/
  /*width: 100%;*/
  /*padding-top: 10px;*/
  /*z-index: 1029;*/
/*}*/
.navbar {
  padding-bottom: .625rem;
  min-height: 53px;
}

</style>
