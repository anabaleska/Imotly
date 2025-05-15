export const fetchAds = async (page, limit, filters = {}) => {
    try {
        let url = `http://localhost:5001/ads?size=${limit}&page=${page}`;

        if (Object.keys(filters).length > 0) {
            const params = new URLSearchParams();

            for (const key in filters) {
                const value = filters[key];
                if (value !== '' && value !== false && value !== null) {
                    params.append(key, value);
                }
            }
            console.log(params.toString());

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

