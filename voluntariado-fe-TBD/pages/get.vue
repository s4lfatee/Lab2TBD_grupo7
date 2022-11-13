<template>
  <v-simple-table>
    <template v-slot:default>
      <thead>
        <tr>
          <th class="text-left">
            Id
          </th>
          <th class="text-left">
            Names
          </th>
          <th class="text-left">

          </th>
          <th class="text-left">

          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="insti in institutions" :key="insti.id_institution" v-bind:id="'institution_'+insti.id_institution">
          <td>{{ insti.id_institution }}</td>
          <td>{{ insti.institution }}</td>
          <td>
            <v-btn depressed color="primary" :to="{ path: 'put', query: {id: insti.id_institution}}">
              Edit
            </v-btn>
          </td>
          <td>
            <v-btn depressed color="error" @click="deleteFunction(insti.id_institution)">
              Delete
            </v-btn>
          </td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
import axios from "axios";
export default {
  name: 'GetAllPage',
  data: () => ({
    institutions: []
  }),
  methods: {
    deleteFunction(id) {
      axios.delete("http://localhost:8081/institucion/" + id)
        .then(response => {
          console.log(response)
          const element = document.getElementById("institucion_" + id);
          element.remove();
        })
    },
  },

  beforeCreate() {
    axios.get("http://localhost:8081/institucion")
      .then(response => {
        this.institutions = response.data;
      }
      );
  },
}
</script>