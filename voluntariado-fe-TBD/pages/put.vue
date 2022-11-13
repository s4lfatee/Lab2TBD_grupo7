<template>
  <div id="app">
  <v-app id="inspire">
    <v-form>
      <v-container grid-list-xl>
        <v-layout wrap>
  
          <v-text-field
        v-model="name"
        :counter="25"
        label="Institucion"
        required
       ></v-text-field>
          
          
        </v-layout>
        <v-btn
        class="mr-4"
        @click="updateFunction()"
      >
        Ingresar 
      </v-btn>
      </v-container>
    </v-form>
  </v-app>
</div>
</template>

<script>
  import axios from "axios";
  export default {
      name : 'GetAllPage',
      data: () => ({
          name: ''
      }),
      methods: {
          updateFunction(){
              axios.put("http://localhost:8081/institutions/"+this.$route.query.id, {
                  institution: this.name
              })
              .then(response => {
                  console.log(response)
              })
          },
      }, 
  }
  </script>
