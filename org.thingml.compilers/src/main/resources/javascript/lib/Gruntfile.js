module.exports = function (grunt) {

    grunt.initConfig({
        // retrieve your project package.json
        pkg: grunt.file.readJSON('package.json'),

        // creates kevlib.json which represents your project Kevoree model
        // by parsing your pkg.main entry point
        kevoree_genmodel: {
            main: {
                options: {
                    quiet: false,
                    verbose: true
                }
            }
        },

        // pushes your model on http://registry.kevoree.org
        kevoree_registry: { src: 'kevlib.json' }
    });

    grunt.loadNpmTasks('grunt-kevoree');
    grunt.loadNpmTasks('grunt-kevoree-genmodel');
    grunt.loadNpmTasks('grunt-kevoree-registry');

    grunt.registerTask('default', 'build');
    grunt.registerTask('build', ['kevoree_genmodel']);
    grunt.registerTask('publish', 'kevoree_registry');
    grunt.registerTask('kev', ['kevoree']);

};