export const fetchAds = async (page, limit, filters = {}) => {
    try {
        // Determine the URL
        let url = `http://localhost:5001/ads?size=${limit}&page=${page}`;

        // If filters are present, build the search query
        if (Object.keys(filters).length > 0) {
            const params = new URLSearchParams();

            for (const key in filters) {
                const value = filters[key];
                if (value !== '' && value !== false && value !== null) {
                    params.append(key, value);
                }
            }
            console.log(params.toString());

            // Append the filter parameters to the URL
            url = `http://localhost:5001/ads?size=${limit}&page=${page}&${params.toString()}`;
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        throw new Error(error.message);
    }
};

//
// export const fetchAds = async (page, limit) => {
//     try {
//         const response = await fetch(`http://localhost:5001/ads?page=${page}&limit=${limit}`);
//         if (!response.ok) {
//             throw new Error('Network response was not ok');
//         }
//         return await response.json();
//     } catch (error) {
//         throw new Error(error.message);
//     }
// };
//
// export const fetchFilteredAds = async (filters) => {
//     try {
//         const params = new URLSearchParams();
//
//         for (const key in filters) {
//             const value = filters[key];
//             if (value !== '' && value !== false && value !== null) {
//                 params.append(key, value);
//             }
//         }
//         console.log(params.toString());
//
//         const response = await fetch(`http://localhost:5001/ads/search?${params.toString()}`);
//         if (!response.ok) {
//             throw new Error('Network response was not ok');
//         }
//         return await response.json();
//     } catch (error) {
//         throw new Error(error.message);
//     }
// };
//

