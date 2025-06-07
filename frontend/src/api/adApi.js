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

        const data = await response.json();
        console.log(data);
        return  {
            ads: data.ads,
            totalPages: data.total
        };
    } catch (error) {
        throw new Error(error.message);
    }
};

export const fetchSavedSearches = async (email) => {
    console.log(email);
    try {
        const response = await fetch(`http://localhost:5001/ads/saved/${email}`);
        if (!response.ok) throw new Error('Failed to fetch saved searches');
        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const deleteSavedSearch = async (id) => {
    try {
        const response = await fetch(`http://localhost:5001/ads/saved/${id}`, {
            method: 'DELETE',
        });

        if (!response.ok) {
            throw new Error('Failed to delete saved search');
        }

        return true;
    } catch (error) {
        console.error(error);
        return false;
    }
};


