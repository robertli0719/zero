var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: "./src/app.tsx",
    output: {
        filename: "./dist/bundle.js",
    },

    // Enable sourcemaps for debugging webpack's output.
    devtool: "source-map",

    resolve: {
        // Add '.ts' and '.tsx' as resolvable extensions.
        extensions: [".webpack.js", ".web.js", ".ts", ".tsx", ".js"]
    },

    module: {
        rules: [
            // All files with a '.ts' or '.tsx' extension will be handled by 'ts-loader'.
            { test: /\.tsx?$/, use: [{ loader: "ts-loader" }] },
            { test: /\.css$/, use: [{ loader: "style-loader" }, { loader: "css-loader" }] },
            { test: /\.scss$/, use: [{ loader: 'style!css!sass' }] },
            {
                test: /\.js$/, loader: "source-map-loader", enforce: "pre", exclude: [
                    /cropper.js/
                ]
            }
        ]
    },

    plugins: [
        new CopyWebpackPlugin([
            {
                from: 'dist/bundle.js',
                to: '../src/main/webapp/js/app.js'
            }
        ])
    ],

    // When importing a module whose path matches one of the following, just
    // assume a corresponding global variable exists and use that instead.
    // This is important because it allows us to avoid bundling all of our
    // dependencies, which allows browsers to cache those libraries between builds.
    externals: {
        "react": "React",
        "react-dom": "ReactDOM"
    },
};